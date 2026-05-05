package faewulf.itemrename.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import faewulf.itemrename.util.loreEditor;
import faewulf.itemrename.util.ownerCheck;
import faewulf.itemrename.util.permission;
import faewulf.itemrename.util.stringParser;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class setLoreLine {

    static public void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("setloreline")
                        .requires(CommandSourceStack::isPlayer)
                        .requires(
                                source -> {
                                    // multiplayer case
                                    if (source.getServer() != null && source.getServer().isDedicatedServer()) {
                                        return Permissions.check(source, permission.SETLORELINE, 1);
                                    } else {
                                        // fallback true for single player world
                                        return true;
                                    }
                                }
                        )
                        .then(Commands.argument("line number", IntegerArgumentType.integer(1, 256))
                                .then(Commands.argument("lore", StringArgumentType.greedyString())
                                        //optional force straight
                                        .executes(setLoreLine::run)
                                )
                        )
        );
    }

    static private int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        int line = IntegerArgumentType.getInteger(context, "line number");

        ServerPlayer player = context.getSource().getPlayerOrException();

        //get holding item
        ItemStack holding = player.getItemInHand(InteractionHand.MAIN_HAND);

        //if not holding anything
        if (holding.isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Component.nullToEmpty("You must hold an item to modify it.")).create();
        }

        ownerCheck.check(player, holding);

        Component formatted;
        try {
            //get input string
            String name = StringArgumentType.getString(context, "lore");

            //parse string to text format
            formatted = stringParser.stringToText(name);

        } catch (IllegalArgumentException exception) {
            throw new SimpleCommandExceptionType(Component.nullToEmpty(exception.getMessage())).create();
        }

        /*
        if (Objects.requireNonNull(Formatting.strip(formatted.getString())).isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Text.of("Invalid string.")).create();
        }
        */

        //holding.set(DataComponentTypes.LORE, formatted);
        loreEditor.setLore(holding, line, formatted);

        return 0;
    }

}
