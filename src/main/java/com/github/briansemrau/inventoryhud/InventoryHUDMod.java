package com.github.briansemrau.inventoryhud;

import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class InventoryHUDMod implements ModInitializer {

	public static final String MOD_ID = "inventory_hud";

	public static ModConfig CONFIG = null;
	private static FabricKeyBinding keyBinding;

	@Override
	public void onInitialize() {
		keyBinding = FabricKeyBinding.Builder.create(
				new Identifier("inventoryhud", "shown"),
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				"Inventory Hud"
		).build();
		InventoryHUDMod.CONFIG = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new).getConfig();
		KeyBindingRegistry.INSTANCE.register(keyBinding);
		ClientTickCallback.EVENT.register(e ->
		{
			if(keyBinding.wasPressed())InventoryHUDMod.CONFIG.show = !InventoryHUDMod.CONFIG.show;
		});
	}

}
