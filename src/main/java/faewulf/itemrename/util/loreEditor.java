package faewulf.itemrename.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class loreEditor {
    public static void setLore(ItemStack stack, int lineIndex, Text loreText) {
        LoreComponent lore = stack.getComponents().get(DataComponentTypes.LORE);

        //if null
        if (lore == null)
            lore = new LoreComponent(new ArrayList<>());

        //get all lores from the array
        List<Text> allLoreLines = new ArrayList<Text>(lore.lines());

        //should fill empty line with null?
        int currentLoreSize = allLoreLines.size();
        while (lineIndex > currentLoreSize) {
            currentLoreSize++;
            allLoreLines.add(Text.of(" "));
        }

        //replace target lore into this item
        allLoreLines.set(lineIndex - 1, loreText);

        //just replace the obj
        lore = new LoreComponent(allLoreLines);
        stack.set(DataComponentTypes.LORE, lore);
    }

    public static void insertLore(ItemStack stack, int lineIndex, Text loreText) {
        LoreComponent lore = stack.getComponents().get(DataComponentTypes.LORE);

        //if null
        if (lore == null)
            lore = new LoreComponent(new ArrayList<>());

        //get all lores from the array
        List<Text> allLoreLines = new ArrayList<Text>(lore.lines());

        //if lineindex > lines
        //should fill empty line with null?
        int currentLoreSize = allLoreLines.size();
        while (lineIndex - 1 > currentLoreSize) {
            currentLoreSize++;
            allLoreLines.add(Text.of(" "));
        }

        //replace target lore into this item
        allLoreLines.add(lineIndex - 1, loreText);

        //just replace the obj
        lore = new LoreComponent(allLoreLines);
        stack.set(DataComponentTypes.LORE, lore);
    }

    public static void addLore(ItemStack stack, Text loreText) {
        LoreComponent lore = stack.getComponents().get(DataComponentTypes.LORE);

        //if null
        if (lore == null)
            lore = new LoreComponent(new ArrayList<>());

        //get all lores from the array
        List<Text> allLoreLines = new ArrayList<Text>(lore.lines());

        //replace target lore into this item
        allLoreLines.add(loreText);

        //just replace the obj
        lore = new LoreComponent(allLoreLines);
        stack.set(DataComponentTypes.LORE, lore);
    }

    public static void removeLore(ItemStack stack) {
        //just replace the obj
        LoreComponent lore = new LoreComponent(new ArrayList<Text>());
        stack.set(DataComponentTypes.LORE, lore);
    }

    public static void removeLoreLine(ItemStack stack, int index) {
        LoreComponent lore = stack.getComponents().get(DataComponentTypes.LORE);

        //if null
        if (lore == null)
            lore = new LoreComponent(new ArrayList<>());

        //get all lores from the array
        List<Text> allLoreLines = new ArrayList<Text>(lore.lines());

        //replace target lore into this item
        if (index <= allLoreLines.size())
            allLoreLines.remove(index - 1);

        //just replace the obj
        lore = new LoreComponent(allLoreLines);
        stack.set(DataComponentTypes.LORE, lore);
    }
}
