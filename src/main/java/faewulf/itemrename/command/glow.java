package faewulf.itemrename.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import faewulf.itemrename.util.ownerCheck;
import faewulf.itemrename.util.permission;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class glow {
    static public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("glow")
                        .requires(CommandSourceStack::isPlayer)
                        .requires(
                                source -> {
                                    // multiplayer case
                                    if (source.getServer() != null && source.getServer().isDedicatedServer()) {
                                        return Permissions.check(source, permission.GLOW, 1);
                                    } else {
                                        // fallback true for single player world
                                        return true;
                                    }
                                }
                        )
                        .executes(glow::run)
        );
    }

    static private int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        ServerPlayer player = context.getSource().getPlayerOrException();

        //get holding item
        ItemStack holding = player.getItemInHand(InteractionHand.MAIN_HAND);

        //if not holding anything
        if (holding.isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Component.nullToEmpty("You must hold an item to modify it.")).create();
        }

        ownerCheck.check(player, holding);

        holding.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);

        return 0;
    }
}
