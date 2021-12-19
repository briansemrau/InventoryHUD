package com.github.briansemrau.inventoryhud;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = InventoryHUDMod.MOD_ID)
public class ModConfig implements ConfigData {

    @Comment("Changes the alpha of the Hud.")
    public float alpha = 1;

    @Comment("Switch between on or off.")
    public boolean show = true;
}
