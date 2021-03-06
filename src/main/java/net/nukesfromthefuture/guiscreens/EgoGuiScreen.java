package net.nukesfromthefuture.guiscreens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.nukesfromthefuture.containers.EgoContainer;
import net.nukesfromthefuture.main.Nukesfromthefuture;

public class EgoGuiScreen extends ContainerScreen<EgoContainer> {
    public static ResourceLocation texture = new ResourceLocation(Nukesfromthefuture.mod_id, "textures/gui/ego_nuke.png");
    public EgoGuiScreen(EgoContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = 178;
        this.ySize = 179;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        minecraft.getTextureManager().bindTexture(texture);
        this.blit(matrixStack, guiLeft, guiTop,0, 0, xSize, ySize);
    }
    //I know it already does it for me, but I DON'T WANT THE WORD "INVENTORY" ABOVE MY INVENTORY BECAUSE THE TEXT INTERSECTS THE PICTURE
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawString(matrixStack, this.title.getString(), this.xSize / 2 - font.getStringWidth(this.title.getString()) / 2, 3, 404040);
    }
}
