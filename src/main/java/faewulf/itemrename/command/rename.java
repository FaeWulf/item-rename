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
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.Objects;

public class rename {
    static public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("rename")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .requires(Permissions.require(permission.RENAME))
                        .then(CommandManager.argument("name", StringArgumentType.greedyString())
                                .executes(rename::run)
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
                    Text.of("You must hold an item to rename it.")).create();
        }

        ownerCheck.check(player, holding);

        Text formatted;
        try {
            //get input string
            String name = StringArgumentType.getString(context, "name");

            //parse string to text format
            formatted = stringParser.stringToText(name);

        } catch (IllegalArgumentException exception) {
            throw new SimpleCommandExceptionType(Text.of(exception.getMessage())).create();
        }

        if (Objects.requireNonNull(Formatting.strip(formatted.getString())).isEmpty()) {
            throw new SimpleCommandExceptionType(
                    Text.of("Invalid name.")).create();
        }

        holding.setCustomName(formatted);

        return 0;
    }
}
