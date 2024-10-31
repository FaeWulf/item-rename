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
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.Objects;

public class setLoreLine {

    static public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("setloreline")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .requires(Permissions.require(permission.SETLORELINE))
                        .then(CommandManager.argument("line number", IntegerArgumentType.integer(1, 256))
                                .then(CommandManager.argument("lore", StringArgumentType.greedyString())
                                        .executes(setLoreLine::run)
                                )
                        )
        );
    }

    static private int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        int line = IntegerArgumentType.getInteger(context, "line number");

        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        //get holding item
        ItemStack holding = player.getStackInHand(Hand.MAIN_HAND);

        //if not holding anything
        if (holding.isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Text.of("You must hold an item to modify it.")).create();
        }

        ownerCheck.check(player, holding);

        Text formatted;
        try {
            //get input string
            String name = StringArgumentType.getString(context, "lore");

            //parse string to text format
            formatted = stringParser.stringToText(name);

        } catch (IllegalArgumentException exception) {
            throw new SimpleCommandExceptionType(Text.of(exception.getMessage())).create();
        }

        if (Objects.requireNonNull(Formatting.strip(formatted.getString())).isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Text.of("Invalid string.")).create();
        }

        //holding.set(DataComponentTypes.LORE, formatted);
        loreEditor.setLore(holding, line, formatted);

        return 0;
    }

}
