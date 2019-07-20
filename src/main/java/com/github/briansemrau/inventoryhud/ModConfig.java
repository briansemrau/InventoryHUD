package com.github.briansemrau.inventoryhud;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.shadowed.blue.endless.jankson.Comment;

@Config(name = InventoryHUDMod.MOD_ID)
public class ModConfig implements ConfigData {

    @Comment("Changes the alpha of the Hud.")
    public float alpha = 0.5f;

    @Comment("Switch between on or off.")
    public boolean show = true;
}
