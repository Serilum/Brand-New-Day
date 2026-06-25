package com.natamus.brandnewday.mixin;

import com.natamus.brandnewday.functions.NewDayFunctions;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPacketListener.class, priority = 1001)
public class ClientPacketListenerMixin {
	@Inject(method = "handleAwardStats", at = @At("TAIL"))
	private void ClientPacketListenerMixin_handleAwardStats(ClientboundAwardStatsPacket packet, CallbackInfo ci) {
		NewDayFunctions.onStatsReceived();
	}
}
