package com.github.briansemrau.inventoryhud.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AbsoluteHand;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    private static final Identifier INVENTORY_TEX = new Identifier("textures/gui/container/inventory.png");

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null; // method content ignored
    }

    @Shadow
    private void renderHotbarItem(int int_1, int int_2, float float_1, PlayerEntity playerEntity_1, ItemStack itemStack_1) {
        // method content ignored
    }

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(F)V"))
    public void onDraw(float float_1, CallbackInfo ci) {
        renderInventory(float_1);
    }

    private void renderInventory(float float_1) {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 0.6F);

            this.client.getTextureManager().bindTexture(INVENTORY_TEX);
            int padding = 5;
            int texWidth = 162;
            int texHeight = 54;
            int u = 7;
            int v = 83;
            int slotSize = 18;
            int width = texWidth;
            int height = texHeight;
            boolean smallScale = false;

            // Smaller screen resolution
            AbsoluteHand hand = playerEntity.getMainHand();
            int hotbarWidth = 182 + (hand == AbsoluteHand.LEFT ? 29 : 0) * 2;
            if (this.scaledWidth < hotbarWidth + (texWidth + padding) * 2) {
                width /= 2;
                height /= 2;
                slotSize /= 2;
                smallScale = true;
            }

            int xLeft = this.scaledWidth - width - padding;
            int yTop = this.scaledHeight - height - padding;
            int yBottom = this.scaledHeight - padding;

            // Draw inventory background
            blit(xLeft, yTop, width, height, u, v, texWidth, texHeight, 256, 256);

            // Draw items
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GuiLighting.enableForItems();

            if (smallScale) {
                GlStateManager.pushMatrix();
                GlStateManager.scalef(0.5F, 0.5F, 1F);
            }
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 9; ++j) {
                    // Draw item
                    int x = xLeft + (j * slotSize);
                    int y = yBottom - ((3 - i) * slotSize);
                    if (smallScale) {
                        x *= 2;
                        y *= 2;
                    }
                    this.renderHotbarItem(x + 1, y + 1, float_1, playerEntity, playerEntity.inventory.main.get((i + 1) * 9 + j));
                }
            }
            if (smallScale) {
                GlStateManager.popMatrix();
            }

            GuiLighting.disable();
            GlStateManager.disableRescaleNormal();
//            GlStateManager.disableBlend();
        }
    }

}
