package com.natamus.brandnewday.gui.module.impl;

import com.natamus.brandnewday.config.ConfigHandler;
import com.natamus.brandnewday.gui.module.NewDayContext;
import com.natamus.brandnewday.gui.module.NewDayModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

public class DayModule implements NewDayModule {
	private static final int SWOOSH_DELAY_TICKS = 18;
	private static final int SWOOSH_LENGTH_TICKS = 11;
	private static final int COLOUR_LENGTH_TICKS = 22;
	private static final float ROLLING_BRIGHTNESS = 0.7f;

	@Override
	public boolean isEnabled() {
		return ConfigHandler.showDayCount;
	}

	@Override
	public void render(GuiGraphics guiGraphics, NewDayContext context) {
		Font font = Minecraft.getInstance().font;
		float scale = ConfigHandler.dayFontScale * context.scale;
		int centerY = context.screenHeight / 2 - (int) (font.lineHeight * scale / 2f);

		String template = Language.getInstance().getOrDefault("collective.brandnewday.day");
		String marker = template.contains("%s") ? "%s" : "%1$s";
		int markerIndex = template.indexOf(marker);
		if (markerIndex < 0) {
			markerIndex = template.length();
			marker = "";
		}

		Component before = Component.literal(template.substring(0, markerIndex));
		Component after = Component.literal(template.substring(markerIndex + marker.length()));
		String currentNumber = Long.toString(context.day);
		String previousNumber = Long.toString(context.day - 1);

		int beforeWidth = font.width(before);
		int numberWidth = font.width(currentNumber);
		int blockWidth = beforeWidth + numberWidth + font.width(after);
		int startX = -blockWidth / 2;
		int numberX = startX + beforeWidth;
		int afterX = numberX + numberWidth;

		boolean hasPreviousDay = context.day >= 1;
		float roll = hasPreviousDay ? progressOver(context.elapsedTicks, SWOOSH_LENGTH_TICKS) : 1f;
		float brightness = hasPreviousDay ? progressOver(context.elapsedTicks, COLOUR_LENGTH_TICKS) : 1f;

		guiGraphics.pose().pushMatrix();
		guiGraphics.pose().translate(context.screenWidth / 2f, centerY);
		guiGraphics.pose().scale(scale, scale);

		guiGraphics.drawString(font, before, startX, 0, fullColour(context.alpha));
		guiGraphics.drawString(font, after, afterX, 0, fullColour(context.alpha));

		if (roll <= 0f) {
			drawNumber(guiGraphics, font, previousNumber, numberX, 0f, numberColour(context.alpha, 0f));
		}
		else if (roll >= 1f) {
			drawNumber(guiGraphics, font, currentNumber, numberX, 0f, numberColour(context.alpha, brightness));
		}
		else {
			float eased = easeOut(roll);
			int outgoing = numberColour((int) (context.alpha * (1f - eased)), 0f);
			int incoming = numberColour((int) (context.alpha * eased), brightness);

			drawNumber(guiGraphics, font, previousNumber, numberX, -eased * font.lineHeight, outgoing);
			drawNumber(guiGraphics, font, currentNumber, numberX, (1f - eased) * font.lineHeight, incoming);
		}

		guiGraphics.pose().popMatrix();
	}

	private void drawNumber(GuiGraphics guiGraphics, Font font, String number, int x, float yOffset, int colour) {
		guiGraphics.pose().pushMatrix();
		guiGraphics.pose().translate(0f, yOffset);
		guiGraphics.drawString(font, Component.literal(number), x, 0, colour);
		guiGraphics.pose().popMatrix();
	}

	private static int fullColour(int alpha) {
		return ARGB.color(alpha, ConfigHandler.dayTextRGB_R, ConfigHandler.dayTextRGB_G, ConfigHandler.dayTextRGB_B);
	}

	private static int numberColour(int alpha, float brightness) {
		float factor = ROLLING_BRIGHTNESS + (1f - ROLLING_BRIGHTNESS) * brightness;
		int red = (int) (ConfigHandler.dayTextRGB_R * factor);
		int green = (int) (ConfigHandler.dayTextRGB_G * factor);
		int blue = (int) (ConfigHandler.dayTextRGB_B * factor);
		return ARGB.color(alpha, red, green, blue);
	}

	private static float progressOver(float elapsedTicks, int length) {
		float progress = (elapsedTicks - SWOOSH_DELAY_TICKS) / length;
		return Math.max(0f, Math.min(1f, progress));
	}

	private static float easeOut(float t) {
		float remaining = 1f - t;
		return 1f - remaining * remaining * remaining;
	}
}
