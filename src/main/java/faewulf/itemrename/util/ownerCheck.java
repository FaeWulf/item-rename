package faewulf.itemrename.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public class ownerCheck {
    static public void check(ServerPlayerEntity player, ItemStack item) throws CommandSyntaxException {

        //if has admin permission then bypass the check
        if (Permissions.check(player, permission.ADMIN))
            return;

        //get data
        NbtCompound customData = item.getNbt();

        if (customData == null)
            return;

        //if not found any related data, then item is safe to pass
        if (!customData.contains("itemrename:authorUUID") || !customData.contains("itemrename:authorName"))
            return;

        //get 2 nbt data
        String itemUUID = customData.getString("itemrename:authorUUID");
        String itemName = customData.getString("itemrename:authorName");

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
        NbtCompound customData = item.getNbt();

        if (customData == null)
            return "unknown";

        if (!customData.contains("itemrename:authorName"))
            return "unknown";

        //get 2 nbt data
        String itemName = customData.getString("itemrename:authorName");

        if (itemName == null)
            return "unknown";

        return itemName;
    }
}
