package faewulf.itemrename.inter;

import net.minecraft.text.Style;

public interface ICustomStyle {
    default Style ItemRename$withCustomFormatting(CustomFormatting... formattings) {
        throw new UnsupportedOperationException("Uninjected interface method");
    }
}
