package com.natamus.brandnewday.gui.module;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface NewDayModule {
	default void onWorldLoad(String worldKey) { }

	default void seedFromStats() { }

	default void onPreview() { }

	default void onShowLast() { }

	default void onNewDay() { }

	default void reset() { }

	boolean isEnabled();

	void render(GuiGraphicsExtractor guiGraphics, NewDayContext context);
}
