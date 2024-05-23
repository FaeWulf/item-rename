package faewulf.itemrename;

import faewulf.itemrename.command.rename;
import faewulf.itemrename.command.setLoreLine;
import faewulf.itemrename.util.ReflectionUtils;
import net.fabricmc.api.ModInitializer;
import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Itemrename implements ModInitializer {
    public static final String MODID = "itemrename";
    public static final Logger LOGGER = LoggerFactory.getLogger("item-rename");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing...");

        loadCommands();
    }

    private void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ReflectionUtils.invokeMethodOnClasses("faewulf.itemrename.command", "register", CommandDispatcher.class, dispatcher);
        });
    }
}