package com.serilum.brandnewday;

import com.serilum.brandnewday.forge.config.IntegrateForgeConfig;
import com.serilum.brandnewday.forge.events.ForgeClientEvents;
import com.serilum.brandnewday.forge.events.ForgeGuiEvents;
import com.serilum.brandnewday.util.Reference;
import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class ModForge {
	
	public ModForge(FMLJavaModLoadingContext modLoadingContext) {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		BusGroup busGroup = modLoadingContext.getModBusGroup();
		FMLLoadCompleteEvent.getBus(busGroup).addListener(this::loadComplete);

		if (FMLEnvironment.dist.equals(Dist.CLIENT)) {
			AddGuiOverlayLayersEvent.BUS.addListener(ForgeGuiEvents::onAddGuiLayers);
		}

		setGlobalConstants();
		ModCommon.init();

		IntegrateForgeConfig.registerScreen(modLoadingContext);

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		if (!FMLEnvironment.dist.equals(Dist.CLIENT)) {
			return;
		}

		ForgeClientEvents.registerEventsInBus();
	}

	private static void setGlobalConstants() {

	}
}