package faewulf.itemrename.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import faewulf.itemrename.util.ownerCheck;
import faewulf.itemrename.util.permission;
import faewulf.itemrename.util.stringParser;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class rename {
    static public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("rename")
                        .requires(CommandSourceStack::isPlayer)
                        .requires(
                                source -> {
                                    // multiplayer case
                                    if (source.getServer() != null && source.getServer().isDedicatedServer()) {
                                        return Permissions.check(source, permission.RENAME, 1);
                                    } else {
                                        // fallback true for single player world
                                        return true;
                                    }
                                }
                        )
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(rename::run)
                        )
        );

    }

    static private int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();

        //get holding item
        ItemStack holding = player.getItemInHand(InteractionHand.MAIN_HAND);

        //if not holding anything
        if (holding.isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Component.nullToEmpty("You must hold an item to rename it.")).create();
        }

        ownerCheck.check(player, holding);

        Component formatted;
        try {
            //get input string
            String name = StringArgumentType.getString(context, "name");

            //parse string to text format
            formatted = stringParser.stringToText(name);

        } catch (IllegalArgumentException exception) {
            throw new SimpleCommandExceptionType(Component.nullToEmpty(exception.getMessage())).create();
        }

        /*
        if (Objects.requireNonNull(Formatting.strip(formatted.getString())).isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Text.of("Invalid name.")).create();
        }
         */

        holding.set(DataComponents.CUSTOM_NAME, formatted);

        return 0;
    }
}
