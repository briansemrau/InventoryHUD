package com.github.briansemrau.inventoryhud;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = InventoryHUDMod.MOD_ID)
public class ModConfig implements ConfigData {

    @Comment("Changes the alpha of the Hud.")
    public float alpha = 1;

    @Comment("Switch between on or off.")
    public boolean show = true;
}
