package com.natamus.brandnewday;

import com.natamus.brandnewday.config.ConfigHandler;
import com.natamus.brandnewday.data.Constants;
import com.natamus.brandnewday.util.Util;
import com.natamus.collective.globalcallbacks.MainMenuLoadedCallback;
import com.natamus.collective.services.Services;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		if (!Services.MODLOADER.isClientSide()) {
			return;
		}

		Util.init();

		MainMenuLoadedCallback.MAIN_MENU_LOADED.register(() -> {
			try {
				Util.initTextureData();
			}
			catch (Exception ex) {
				Constants.logger.warn(Constants.logPrefix + "Something went wrong while loading the header image.");
				ex.printStackTrace();
			}
		});
	}
}