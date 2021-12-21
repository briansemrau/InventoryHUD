package com.github.briansemrau.inventoryhud.mixin;

import com.github.briansemrau.inventoryhud.InventoryHUDMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
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
    private void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
        // method content ignored
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V"))
    public void onDraw(MatrixStack matrixStack, float float_1, CallbackInfo ci) {
        renderInventory(float_1, matrixStack);
    }

    private void renderInventory(float float_1, MatrixStack matrixStack) {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, InventoryHUDMod.CONFIG.alpha);

            //this.client.getTextureManager().bindTexture(INVENTORY_TEX);
            RenderSystem.setShaderTexture(0, INVENTORY_TEX);
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
            Arm hand = playerEntity.getMainArm();
            int hotbarWidth = 182 + (hand == Arm.LEFT ? 29 : 0) * 2;
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
            if (InventoryHUDMod.CONFIG.show) {
                drawTexture(matrixStack, xLeft, yTop, width, height, u, v, texWidth, texHeight, 256, 256);

                // Draw items
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                if (smallScale) {
                    matrixStack.push();
                    matrixStack.scale(0.5F, 0.5F, 1.0F);
                }
                int m = 1;
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        // Draw item
                        int x = xLeft + (j * slotSize);
                        int y = yBottom - ((3 - i) * slotSize);
                        if (smallScale) {
                            x *= 2;
                            y *= 2;
                        }
                        this.renderHotbarItem(x + 1, y + 1, float_1, playerEntity, playerEntity.getInventory().main.get((i + 1) * 9 + j), m++);
                    }
                }
                if (smallScale) {
                    matrixStack.pop();
                }
            }

            RenderSystem.enableBlend();
        }
    }

}
