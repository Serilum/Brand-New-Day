package com.natamus.brandnewday.mixin;

import com.natamus.brandnewday.gui.NewDayOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class, priority = 1001)
public class GuiMixin {
	@Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
	private void GuiMixin_extractCrosshair(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		if (NewDayOverlay.isActive()) {
			ci.cancel();
		}
	}
}
