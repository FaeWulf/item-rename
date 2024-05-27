package faewulf.itemrename.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

public class loreEditor {
    public static void setLore(ItemStack stack, int lineIndex, Text loreText) {

        //get lores nbt
        NbtList lore = stack.getOrCreateSubNbt("display").getList("Lore", NbtElement.STRING_TYPE);

        //should fill empty line with null?
        int currentLoreSize = lore.size();
        while (lineIndex > currentLoreSize) {
            currentLoreSize++;
            lore.add(NbtString.of(Text.Serialization.toJsonString(Text.of(" "))));
        }


        //replace target lore into this item
        lore.set(lineIndex - 1, NbtString.of(Text.Serialization.toJsonString(loreText)));

        //just replace the obj
        stack.getOrCreateSubNbt("display").put("Lore", lore);
    }

    public static void insertLore(ItemStack stack, int lineIndex, Text loreText) {

        //get lores nbt
        NbtList lore = stack.getOrCreateSubNbt("display").getList("Lore", NbtElement.STRING_TYPE);

        //should fill empty line with null?
        int currentLoreSize = lore.size();
        while (lineIndex > currentLoreSize) {
            currentLoreSize++;
            lore.add(NbtString.of(Text.Serialization.toJsonString(Text.of(" "))));
        }

        //replace target lore into this item
        lore.add(lineIndex - 1, NbtString.of(Text.Serialization.toJsonString(loreText)));

        //just replace the obj
        stack.getOrCreateSubNbt("display").put("Lore", lore);
    }

    public static void addLore(ItemStack stack, Text loreText) {

        //get lores nbt
        NbtList lore = stack.getOrCreateSubNbt("display").getList("Lore", NbtElement.STRING_TYPE);

        //replace target lore into this item
        lore.add(NbtString.of(Text.Serialization.toJsonString(loreText)));

        //just replace the obj
        stack.getOrCreateSubNbt("display").put("Lore", lore);
    }

    public static void removeLore(ItemStack stack) {
        //just replace the obj
        NbtList lore = new NbtList();
        stack.getOrCreateSubNbt("display").put("Lore", lore);
    }

    public static void removeLoreLine(ItemStack stack, int index) {
        //get lores nbt
        NbtList lore = stack.getOrCreateSubNbt("display").getList("Lore", NbtElement.STRING_TYPE);


        //replace target lore into this item
        if (!lore.isEmpty() && index <= lore.size())
            lore.remove(index - 1);

        //just replace the obj
        stack.getOrCreateSubNbt("display").put("Lore", lore);
    }
}
