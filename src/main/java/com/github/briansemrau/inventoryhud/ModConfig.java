package com.github.briansemrau.inventoryhud;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1.shadowed.blue.endless.jankson.Comment;

@Config(name = InventoryHUDMod.MOD_ID)
public class ModConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    @Comment("Changes the alpha of the Hud.")
    public float opacity = 1;
}
