package com.serilum.brandnewday.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.natamus.collective.functions.DataFunctions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Util {
	public static final ResourceLocation HEADER_TEXTURE = new ResourceLocation(Reference.MOD_ID, "header");

	private static final String rootConfigPath = DataFunctions.getConfigDirectory() + File.separator + Reference.MOD_ID;
	private static final File imageFile = new File(rootConfigPath + File.separator + "header" + File.separator + "header.png");
	private static final File headerDir = imageFile.getParentFile();

	private static boolean headerLoaded = false;
	private static int headerWidth, headerHeight;

	public static void init() {
		if (!headerDir.isDirectory()) {
			boolean ignored = headerDir.mkdirs();
		}
	}

	public static void initTextureData() throws IOException {
		if (!imageFile.exists()) {
			InputStream defaultStream = Util.class.getResourceAsStream("/assets/brandnewday/textures/header/header.png");
			Files.copy(defaultStream, imageFile.toPath());
			defaultStream.close();
		}

		InputStream imageStream = new FileInputStream(imageFile);
		NativeImage image = NativeImage.read(imageStream);
		imageStream.close();

		headerWidth = image.getWidth();
		headerHeight = image.getHeight();

		Minecraft.getInstance().getTextureManager().register(HEADER_TEXTURE, new DynamicTexture(image));
		headerLoaded = true;
	}

	public static boolean isHeaderLoaded() {
		return headerLoaded;
	}

	public static int getHeaderWidth() {
		return headerWidth;
	}

	public static int getHeaderHeight() {
		return headerHeight;
	}
}
