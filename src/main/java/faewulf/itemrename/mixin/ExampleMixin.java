package faewulf.itemrename.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ExampleMixin {
    @Inject(at = @At("HEAD"), method = "hasGlint")
    private void init(CallbackInfoReturnable<Boolean> cir) {
    }
}