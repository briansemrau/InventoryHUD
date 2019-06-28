package com.github.briansemrau.inventoryhud;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.shadowed.blue.endless.jankson.Comment;

@Config(name = InventoryHUDMod.MOD_ID)
public class ModConfig implements ConfigData {

    @Comment("Changes the x-axis of the hud")
    public float x;

    @Comment("Changes the y-axis of the hud")
    public float y;

    @Comment("Changes the alpha of the Hud.")
    public float alpha = 1;

    @Comment("Switch between on or off.")
    public boolean show = true;
}
