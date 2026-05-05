package faewulf.itemrename.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

public class loreEditor {
    public static void setLore(ItemStack stack, int lineIndex, Component loreText) {
        ItemLore lore = stack.getComponents().get(DataComponents.LORE);

        //if null
        if (lore == null)
            lore = new ItemLore(new ArrayList<>());

        //get all lores from the array
        List<Component> allLoreLines = new ArrayList<Component>(lore.lines());

        //should fill empty line with null?
        int currentLoreSize = allLoreLines.size();
        while (lineIndex > currentLoreSize) {
            currentLoreSize++;
            allLoreLines.add(Component.nullToEmpty(" "));
        }

        //replace target lore into this item
        allLoreLines.set(lineIndex - 1, loreText);

        //just replace the obj
        lore = new ItemLore(allLoreLines);
        stack.set(DataComponents.LORE, lore);
    }

    public static void insertLore(ItemStack stack, int lineIndex, Component loreText) {
        ItemLore lore = stack.getComponents().get(DataComponents.LORE);

        //if null
        if (lore == null)
            lore = new ItemLore(new ArrayList<>());

        //get all lores from the array
        List<Component> allLoreLines = new ArrayList<Component>(lore.lines());

        //if lineindex > lines
        //should fill empty line with null?
        int currentLoreSize = allLoreLines.size();
        while (lineIndex - 1 > currentLoreSize) {
            currentLoreSize++;
            allLoreLines.add(Component.nullToEmpty(" "));
        }

        //replace target lore into this item
        allLoreLines.add(lineIndex - 1, loreText);

        //just replace the obj
        lore = new ItemLore(allLoreLines);
        stack.set(DataComponents.LORE, lore);
    }

    public static void addLore(ItemStack stack, Component loreText) {
        ItemLore lore = stack.getComponents().get(DataComponents.LORE);

        //if null
        if (lore == null)
            lore = new ItemLore(new ArrayList<>());

        //get all lores from the array
        List<Component> allLoreLines = new ArrayList<Component>(lore.lines());

        //replace target lore into this item
        allLoreLines.add(loreText);

        //just replace the obj
        lore = new ItemLore(allLoreLines);
        stack.set(DataComponents.LORE, lore);
    }

    public static void removeLore(ItemStack stack) {
        //just replace the obj
        ItemLore lore = new ItemLore(new ArrayList<Component>());
        stack.set(DataComponents.LORE, lore);
    }

    public static void removeLoreLine(ItemStack stack, int index) {
        ItemLore lore = stack.getComponents().get(DataComponents.LORE);

        //if null
        if (lore == null)
            lore = new ItemLore(new ArrayList<>());

        //get all lores from the array
        List<Component> allLoreLines = new ArrayList<Component>(lore.lines());

        //replace target lore into this item
        if (index <= allLoreLines.size())
            allLoreLines.remove(index - 1);

        //just replace the obj
        lore = new ItemLore(allLoreLines);
        stack.set(DataComponents.LORE, lore);
    }
}
