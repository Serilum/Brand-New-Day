package com.serilum.brandnewday.gui.module;

public class NewDayContext {
	public static final int SPACING = 4;

	public final int screenWidth;
	public final int screenHeight;
	public final int alpha;
	public final long day;
	public final float elapsedTicks;
	public final float scale;

	public NewDayContext(int screenWidth, int screenHeight, int alpha, long day, float elapsedTicks, float scale) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.alpha = alpha;
		this.day = day;
		this.elapsedTicks = elapsedTicks;
		this.scale = scale;
	}
}
