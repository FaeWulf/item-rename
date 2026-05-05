package faewulf.itemrename.inter;

import net.minecraft.network.chat.Style;

public interface ICustomStyle {
    default Style ItemRename$withCustomFormatting(CustomFormatting... formattings) {
        throw new UnsupportedOperationException("Uninjected interface method");
    }
}
