package com.serilum.brandnewday;

import com.serilum.brandnewday.config.ConfigHandler;
import com.serilum.brandnewday.data.Constants;
import com.serilum.brandnewday.util.Util;
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