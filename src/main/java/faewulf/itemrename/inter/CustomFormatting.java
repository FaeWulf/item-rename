package faewulf.itemrename.inter;

import net.minecraft.util.Formatting;

public class CustomFormatting {
    private int customColorCode = -1;
    private Formatting formatting = null;

    public CustomFormatting(int customColorCode) {
        this.customColorCode = customColorCode;
    }

    public CustomFormatting(Formatting formatting) {
        this.formatting = formatting;
    }

    public boolean isFormatting() {
        return formatting != null;
    }

    public boolean isCustomColor() {
        return customColorCode != -1;
    }


    public Formatting getFormatting() {
        return formatting;
    }

    public int getCustomColorCode() {
        return customColorCode;
    }
}
