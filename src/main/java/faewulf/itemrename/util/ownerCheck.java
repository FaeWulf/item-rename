package faewulf.itemrename.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class ownerCheck {
    static public void check(ServerPlayerEntity player, ItemStack item) throws CommandSyntaxException {

        //if has admin permission then bypass the check
        if (Permissions.check(player, permission.ADMIN))
            return;

        //get data
        NbtComponent customData = item.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);

        //if not found any related data, then item is safe to pass
        if (!customData.contains("itemrename:authorUUID") || !customData.contains("itemrename:authorName"))
            return;

        //get 2 nbt data
        String itemUUID = customData.copyNbt().getString("itemrename:authorUUID");
        String itemName = customData.copyNbt().getString("itemrename:authorName");

        //null proof
        if (itemName == null)
            itemName = "unknown";

        if (itemUUID == null)
            return;

        String userUUID = player.getUuid().toString();

        //if not the same then isn't item's owner
        if (!Objects.equals(userUUID, itemUUID)) {
            throw new SimpleCommandExceptionType(
                    Text.of("You can't edit this item. Item's Owner: " + itemName)).create();
        }
    }

    static public String getOwnerName(ItemStack item) {
        NbtComponent customData = item.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);

        if (!customData.contains("itemrename:authorName"))
            return "unknown";

        //get 2 nbt data
        String itemName = customData.copyNbt().getString("itemrename:authorName");

        if (itemName == null)
            return "unknown";

        return itemName;
    }
}
