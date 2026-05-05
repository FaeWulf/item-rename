package faewulf.itemrename.inter;

import net.minecraft.ChatFormatting;

public class CustomFormatting {
    private int customColorCode = -1;
    private ChatFormatting formatting = null;

    public CustomFormatting(int customColorCode) {
        this.customColorCode = customColorCode;
    }

    public CustomFormatting(ChatFormatting formatting) {
        this.formatting = formatting;
    }

    public boolean isFormatting() {
        return formatting != null;
    }

    public boolean isCustomColor() {
        return customColorCode != -1;
    }


    public ChatFormatting getFormatting() {
        return formatting;
    }

    public int getCustomColorCode() {
        return customColorCode;
    }
}
