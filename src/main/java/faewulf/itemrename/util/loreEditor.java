package faewulf.itemrename.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;

import java.util.List;

public class loreEditor {
    public static void setLore(ItemStack stack, int lineIndex, Text loreText) {
        NbtCompound itemNbt = stack.getOrCreateSubNbt("display");

        NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
        if (lore.size() + 1 <= lineIndex) {
            return;
        }

        lore.set(lineIndex - 1, NbtString.of(Text.Serializer.toJson(loreText)));
        itemNbt.put("Lore", lore);
    }

    public static void insertLore(ItemStack stack, int lineIndex, Text loreText) {
        NbtCompound itemNbt = stack.getOrCreateSubNbt("display");

        NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
        if (lore.size() + 1 <= lineIndex) {
            return;
        }
        lore.addElement(lineIndex - 1, NbtString.of(Text.Serializer.toJson(loreText)));;
        itemNbt.put("Lore", lore);
    }

    public static void addLore(ItemStack stack, Text loreText) {
        NbtCompound itemNbt = stack.getOrCreateSubNbt("display");

        NbtList lore = new NbtList();
        if (itemNbt.contains("Lore")) {
            lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
        }

        lore.add(NbtString.of(Text.Serializer.toJson(loreText)));
        itemNbt.put("Lore", lore);
    }

    public static void removeLore(ItemStack stack) {
        NbtCompound itemNbt = stack.getOrCreateSubNbt("display");

        NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
        lore.clear();
        itemNbt.put("Lore", lore);
    }

    public static void removeLoreLine(ItemStack stack, int index) {
        NbtCompound itemNbt = stack.getOrCreateSubNbt("display");

        if (!itemNbt.contains("Lore")) {
            return;
        }

        NbtList lore = itemNbt.getList("Lore", NbtElement.STRING_TYPE);
        if (lore.size() + 1 <= index) {
            return;
        }

        lore.remove(index - 1);
        itemNbt.put("Lore", lore);
    }
}
