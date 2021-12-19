package com.github.briansemrau.inventoryhud;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class InventoryHUDMod implements ModInitializer {

	public static final String MOD_ID = "inventory_hud";

	public static ModConfig CONFIG = null;

	@Override
	public void onInitialize() {
		InventoryHUDMod.CONFIG = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new).getConfig();
	}

}
