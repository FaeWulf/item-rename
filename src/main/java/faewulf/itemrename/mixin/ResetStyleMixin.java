package faewulf.itemrename.mixin;

import faewulf.itemrename.util.stringParser;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Style.class)
public class ResetStyleMixin {

    @Shadow
    @Final
    @Nullable TextColor color;
    @Shadow
    @Final
    @Nullable Integer shadowColor;
    @Shadow
    @Final
    @Nullable Boolean bold;
    @Shadow
    @Final
    @Nullable Boolean italic;
    @Shadow
    @Final
    @Nullable Boolean strikethrough;
    @Shadow
    @Final
    @Nullable Boolean underlined;
    @Shadow
    @Final
    @Nullable Boolean obfuscated;
    @Shadow
    @Final
    @Nullable ClickEvent clickEvent;
    @Shadow
    @Final
    @Nullable HoverEvent hoverEvent;
    @Shadow
    @Final
    @Nullable String insertion;
    @Shadow
    @Final
    @Nullable Identifier font;

    @Invoker("<init>")
    static Style create(@Nullable TextColor color, @Nullable Integer shadowColor, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent, @Nullable String insertion, @Nullable Identifier font) {
        throw new AssertionError();
    }

    /**
     * @author Faewulf
     * @reason Reset case makes no sense!
     */
    @Overwrite
    public Style withFormatting(Formatting... formattings) {
        TextColor textColor = this.color;
        Boolean boolean_ = this.bold;
        Boolean boolean2 = this.italic;
        Boolean boolean3 = this.strikethrough;
        Boolean boolean4 = this.underlined;
        Boolean boolean5 = this.obfuscated;

        for (Formatting formatting : formattings) {
            switch (formatting) {
                case OBFUSCATED:
                    boolean5 = true;
                    break;
                case BOLD:
                    boolean_ = true;
                    break;
                case STRIKETHROUGH:
                    boolean3 = true;
                    break;
                case UNDERLINE:
                    boolean4 = true;
                    break;
                case ITALIC:
                    boolean2 = true;
                    break;
                case RESET: {
                    boolean_ = false;
                    boolean2 = false;
                    boolean3 = false;
                    boolean4 = false;
                    boolean5 = false;
                    textColor = null;
                }
                default:
                    textColor = TextColor.fromFormatting(formatting);
            }
        }

        if (stringParser.customColor > 0) {
            textColor = TextColor.fromRgb(stringParser.customColor);
        }

        return create(textColor, this.shadowColor, boolean_, boolean2, boolean4, boolean3, boolean5, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

}
