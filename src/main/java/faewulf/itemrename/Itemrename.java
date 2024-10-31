package faewulf.itemrename;

import faewulf.itemrename.command.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
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
            addLoreLine.register(dispatcher);
            glow.register(dispatcher);
            hideEnchant.register(dispatcher);
            insertLoreLine.register(dispatcher);
            lockItem.register(dispatcher);
            removeGlow.register(dispatcher);
            removeLore.register(dispatcher);
            removeLoreLine.register(dispatcher);
            removeName.register(dispatcher);
            rename.register(dispatcher);
            setLoreLine.register(dispatcher);
            unhideEnchant.register(dispatcher);
            unlockItem.register(dispatcher);
        });
    }
}