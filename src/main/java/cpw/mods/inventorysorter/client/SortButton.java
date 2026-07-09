package cpw.mods.inventorysorter.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class SortButton extends Button {
    
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("inventorysorter", "textures/gui/sort_button.png");
    
    public SortButton(int x, int y, OnPress onPress) {
        super(x, y, 18, 18, Component.translatable("inventorysorter.gui.sort"), onPress, Button.DEFAULT_NARRATION);
    }
    
    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        
        // 1.20.1: 使用 getResourceManager() 检查资源是否存在
        if (mc.getResourceManager().getResource(BUTTON_TEXTURE).isPresent()) {
            // 1.20.1: 使用 isHovered() 替代手动计算
            int v = this.isHovered() ? 18 : 0;
            if (this.isHovered() && isMouseDown(mouseX, mouseY)) {
                v = 36;
            }
            
            // 1.20.1: 使用 GuiGraphics.blit() 替代 GuiComponent.blit()
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            guiGraphics.blit(BUTTON_TEXTURE, this.getX(), this.getY(), 0, v, this.width, this.height, 18, 54);
        } else {
            // Fallback to default button rendering
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        }
    }
    
    // 辅助方法：判断鼠标按下状态
    private boolean isMouseDown(int mouseX, int mouseY) {
        return this.isHovered() && Minecraft.getInstance().mouseHandler.isLeftPressed();
    }
    
    @Override
    public void onPress() {
        super.onPress();
        // Play click sound
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
