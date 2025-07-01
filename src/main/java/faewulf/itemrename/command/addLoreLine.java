package faewulf.itemrename.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import faewulf.itemrename.util.loreEditor;
import faewulf.itemrename.util.ownerCheck;
import faewulf.itemrename.util.permission;
import faewulf.itemrename.util.stringParser;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class addLoreLine {

    static public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("addloreline")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .requires(
                                source -> {
                                    // multiplayer case
                                    if (source.getServer().isDedicated()) {
                                        return Permissions.check(source, permission.ADDLORELINE, 1);
                                    } else {
                                        // fallback true for single player world
                                        return true;
                                    }
                                }
                        )
                        .then(CommandManager.argument("lore", StringArgumentType.greedyString())
                                .executes(addLoreLine::run)
                        )
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

        List<Text> formatted = new ArrayList<>();
        try {
            //get input string
            String name = StringArgumentType.getString(context, "lore");

            // Break down the string using \n
            String[] list = name.split("\\\\n");

            //parse string to text format
            for (String s : list) {
                formatted.add(stringParser.stringToText(s));
            }


        } catch (IllegalArgumentException exception) {
            throw new SimpleCommandExceptionType(Text.of(exception.getMessage())).create();
        }

//        if (Objects.requireNonNull(Formatting.strip(formatted.getString())).isEmpty()) {
//            throw new SimpleCommandExceptionType(
//                    Text.of("Invalid string.")).create();
//        }

        //holding.set(DataComponentTypes.LORE, formatted);

        for (Text text : formatted) {
            loreEditor.addLore(holding, text);
        }

        return 0;
    }

}
