package faewulf.itemrename.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import faewulf.itemrename.util.ownerCheck;
import faewulf.itemrename.util.permission;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Uuids;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.UUID;

public class lockItem {
    static public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("lockitem")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .requires(Permissions.require(permission.LOCK))
                        .executes(lockItem::run)
        );
    }

    static private int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        //get holding item
        ItemStack holding = player.getStackInHand(Hand.MAIN_HAND);


        //if not holding anything
        if (holding.isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Text.of("You must hold an item to modify it.")).create();
        }

        ownerCheck.check(player, holding);

        //set tag
        holding.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> {
            currentNbt.putString("itemrename:authorUUID", player.getUuid().toString());
            currentNbt.putString("itemrename:authorName", player.getName().getString());
        }));

        return 0;
    }
}
