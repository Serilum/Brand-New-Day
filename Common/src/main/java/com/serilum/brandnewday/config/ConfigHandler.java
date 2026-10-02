package com.serilum.brandnewday.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.brandnewday.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<>();

	@Entry public static boolean showHeader = true;
	@Entry public static boolean showDayCount = true;
	@Entry public static boolean showStats = true;
	@Entry public static boolean playSound = true;

	@Entry(min = 1, max = 1200) public static int newDayScreenDurationTicks = 200;
	@Entry(min = 1, max = 200) public static int newDayScreenFadeInTicks = 20;
	@Entry(min = 1, max = 200) public static int newDayScreenFadeOutTicks = 20;

	@Entry(min = 0.1, max = 5.0) public static float guiScaleModifier = 1.0f;

	@Entry(min = 0, max = 2000) public static int headerImageWidth = 64;
	@Entry(min = 0, max = 2000) public static int headerImageHeight = 64;
	@Entry(min = 0, max = 1000) public static int headerImageYOffset = 10;

	@Entry(min = 1, max = 8) public static int dayFontScale = 3;
	@Entry(min = 0, max = 255) public static int dayTextRGB_R = 218;
	@Entry(min = 0, max = 255) public static int dayTextRGB_G = 164;
	@Entry(min = 0, max = 255) public static int dayTextRGB_B = 40;

	@Entry public static boolean showMobsKilled = true;
	@Entry public static boolean showBlocksBroken = true;
	@Entry public static boolean showDistanceTraveled = true;
	@Entry public static boolean showDeaths = true;
	@Entry public static boolean showItemsCrafted = true;
	@Entry(min = 0, max = 255) public static int statsKeyTextRGB_R = 216;
	@Entry(min = 0, max = 255) public static int statsKeyTextRGB_G = 196;
	@Entry(min = 0, max = 255) public static int statsKeyTextRGB_B = 180;
	@Entry(min = 0, max = 255) public static int statsValueTextRGB_R = 255;
	@Entry(min = 0, max = 255) public static int statsValueTextRGB_G = 234;
	@Entry(min = 0, max = 255) public static int statsValueTextRGB_B = 210;

	public static void initConfig() {
		configMetaData.put("showHeader", Arrays.asList(
			"If the header image should be shown at the top of the new day screen."
		));
		configMetaData.put("showDayCount", Arrays.asList(
			"If the current day number should be shown on the new day screen."
		));
		configMetaData.put("showStats", Arrays.asList(
			"If a summary of the previous day should be shown. Each line can be toggled individually below."
		));
		configMetaData.put("playSound", Arrays.asList(
			"If a sound plays when the new day screen appears."
		));

		configMetaData.put("newDayScreenDurationTicks", Arrays.asList(
			"How many ticks the new day screen stays on screen in total. 20 ticks = 1 second."
		));
		configMetaData.put("newDayScreenFadeInTicks", Arrays.asList(
			"How many ticks the screen takes to fade in. 20 ticks = 1 second."
		));
		configMetaData.put("newDayScreenFadeOutTicks", Arrays.asList(
			"How many ticks the screen takes to fade out before disappearing. 20 ticks = 1 second."
		));

		configMetaData.put("guiScaleModifier", Arrays.asList(
			"Scales the day number, stats text, and header image together, based on the player's GUI scale. 1.0 matches the configured sizes at GUI scale 4; higher makes everything bigger."
		));

		configMetaData.put("headerImageWidth", Arrays.asList(
			"The width in pixels the header image is rendered at on screen."
		));
		configMetaData.put("headerImageHeight", Arrays.asList(
			"The height in pixels the header image is rendered at on screen."
		));
		configMetaData.put("headerImageYOffset", Arrays.asList(
			"How far down from the top of the screen the header image is drawn, in pixels. 0 is flush against the top."
		));

		configMetaData.put("dayFontScale", Arrays.asList(
			"How much bigger the day number text should be rendered. 1 is the normal font size."
		));
		configMetaData.put("dayTextRGB_R", Arrays.asList(
			"The red RGB value for the day number text."
		));
		configMetaData.put("dayTextRGB_G", Arrays.asList(
			"The green RGB value for the day number text."
		));
		configMetaData.put("dayTextRGB_B", Arrays.asList(
			"The blue RGB value for the day number text."
		));

		configMetaData.put("showMobsKilled", Arrays.asList(
			"If the monsters slain line is shown in the day summary."
		));
		configMetaData.put("showBlocksBroken", Arrays.asList(
			"If the blocks broken line is shown in the day summary."
		));
		configMetaData.put("showDistanceTraveled", Arrays.asList(
			"If the distance traveled line is shown in the day summary."
		));
		configMetaData.put("showDeaths", Arrays.asList(
			"If the deaths line is shown in the day summary."
		));
		configMetaData.put("showItemsCrafted", Arrays.asList(
			"If the items crafted line is shown in the day summary."
		));
		configMetaData.put("statsKeyTextRGB_R", Arrays.asList(
			"The red RGB value for the stat labels (the key text)."
		));
		configMetaData.put("statsKeyTextRGB_G", Arrays.asList(
			"The green RGB value for the stat labels (the key text)."
		));
		configMetaData.put("statsKeyTextRGB_B", Arrays.asList(
			"The blue RGB value for the stat labels (the key text)."
		));
		configMetaData.put("statsValueTextRGB_R", Arrays.asList(
			"The red RGB value for the stat values (the numbers)."
		));
		configMetaData.put("statsValueTextRGB_G", Arrays.asList(
			"The green RGB value for the stat values (the numbers)."
		));
		configMetaData.put("statsValueTextRGB_B", Arrays.asList(
			"The blue RGB value for the stat values (the numbers)."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}
