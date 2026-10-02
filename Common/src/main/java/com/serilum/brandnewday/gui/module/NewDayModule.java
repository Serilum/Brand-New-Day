package com.serilum.brandnewday.gui.module;

import net.minecraft.client.gui.GuiGraphics;

public interface NewDayModule {
	default void onWorldLoad(String worldKey) { }

	default void seedFromStats() { }

	default void onPreview() { }

	default void onShowLast() { }

	default void onNewDay() { }

	default void reset() { }

	boolean isEnabled();

	void render(GuiGraphics guiGraphics, NewDayContext context);
}
