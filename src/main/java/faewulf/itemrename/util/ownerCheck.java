package faewulf.itemrename.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import java.util.Objects;

public class ownerCheck {
    static public void check(ServerPlayer player, ItemStack item) throws CommandSyntaxException {

        //if has admin permission then bypass the check
        if (Permissions.check(player, permission.ADMIN, 3))
            return;

        //get data
        CustomData customData = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag customDataRef = customData.copyTag();

        //if not found any related data, then item is safe to pass
        if (!customDataRef.contains("itemrename:authorUUID") || !customDataRef.contains("itemrename:authorName"))
            return;

        //get 2 nbt data
        String itemUUID = customData.copyTag().getString("itemrename:authorUUID").orElse(null);
        String itemName = customData.copyTag().getString("itemrename:authorName").orElse(null);

        //null proof
        if (itemName == null)
            itemName = "unknown";

        if (itemUUID == null)
            return;

        String userUUID = player.getUUID().toString();

        //if not the same then isn't item's owner
        if (!Objects.equals(userUUID, itemUUID)) {
            throw new SimpleCommandExceptionType(
                    Component.nullToEmpty("You can't edit this item. Item's Owner: " + itemName)).create();
        }
    }

    static public String getOwnerName(ItemStack item) {
        CustomData customData = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag customDataRef = customData.copyTag();

        if (!customDataRef.contains("itemrename:authorName"))
            return "unknown";

        //get 2 nbt data
        String itemName = customData.copyTag().getString("itemrename:authorName").orElse(null);

        if (itemName == null)
            return "unknown";

        return itemName;
    }
}
