package com.serilum.brandnewday.gui.module.impl;

import com.serilum.brandnewday.config.ConfigHandler;
import com.serilum.brandnewday.gui.module.NewDayContext;
import com.serilum.brandnewday.gui.module.NewDayModule;
import com.serilum.brandnewday.util.Util;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;

public class HeaderModule implements NewDayModule {
	@Override
	public boolean isEnabled() {
		return ConfigHandler.showHeader && Util.isHeaderLoaded();
	}

	@Override
	public void render(GuiGraphicsExtractor guiGraphics, NewDayContext context) {
		int width = Math.round(ConfigHandler.headerImageWidth * context.scale);
		int height = Math.round(ConfigHandler.headerImageHeight * context.scale);
		int x = (context.screenWidth - width) / 2;
		int y = Math.round(ConfigHandler.headerImageYOffset * context.scale);

		int textureWidth = Util.getHeaderWidth();
		int textureHeight = Util.getHeaderHeight();

		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Util.HEADER_TEXTURE, x, y, 0f, 0f, width, height, textureWidth, textureHeight, textureWidth, textureHeight, ARGB.white(context.alpha));
	}
}
