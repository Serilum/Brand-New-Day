package com.serilum.brandnewday.gui.module.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.serilum.brandnewday.config.ConfigHandler;
import com.serilum.brandnewday.gui.module.NewDayContext;
import com.serilum.brandnewday.gui.module.NewDayModule;
import com.serilum.brandnewday.util.Util;
import net.minecraft.client.gui.GuiGraphics;

public class HeaderModule implements NewDayModule {
	@Override
	public boolean isEnabled() {
		return ConfigHandler.showHeader && Util.isHeaderLoaded();
	}

	@Override
	public void render(GuiGraphics guiGraphics, NewDayContext context) {
		int width = Math.round(ConfigHandler.headerImageWidth * context.scale);
		int height = Math.round(ConfigHandler.headerImageHeight * context.scale);
		int x = (context.screenWidth - width) / 2;
		int y = Math.round(ConfigHandler.headerImageYOffset * context.scale);

		int textureWidth = Util.getHeaderWidth();
		int textureHeight = Util.getHeaderHeight();

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderColor(1f, 1f, 1f, context.alpha / 255f);
		guiGraphics.blit(Util.HEADER_TEXTURE, x, y, width, height, 0f, 0f, textureWidth, textureHeight, textureWidth, textureHeight);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.disableBlend();
	}
}
