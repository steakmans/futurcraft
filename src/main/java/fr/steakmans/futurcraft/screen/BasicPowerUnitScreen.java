package fr.steakmans.futurcraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.screen.container.BasicPowerUnitContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class BasicPowerUnitScreen extends AbstractContainerScreen<BasicPowerUnitContainer> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "/textures/gui/container/basic_power_unit.png");

    private ExtendedButton btn;

    public BasicPowerUnitScreen(BasicPowerUnitContainer container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 165;
    }

    @Override
    protected void renderBg(PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {

    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);

        final int scaledEnergy = (int) mapNumber(this.menu.data.get(0), 0, this.menu.data.get(1), 0, 53);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(stack, this.leftPos + 72, this.topPos + 70 - scaledEnergy, 176, 53 - scaledEnergy, 208 - 175, scaledEnergy);

        font.draw(stack, playerInventoryTitle, this.leftPos + 8, this.topPos + 74, 0x404040);
        font.draw(stack, title, this.leftPos + 8, this.topPos + 5, 0x404040);

        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        /*btn = this.addRenderableWidget(new ExtendedButton(leftPos, topPos, 25, 32, new TextComponent("beans"), (btn) -> {
            //do stuff
            System.out.println("yo");
        }));*/
    }

    public static double mapNumber(double value, double rangeMin, double rangeMax, double resultMin, double resultMax) {
        return (value - rangeMin) / (rangeMax - rangeMin) * (resultMax - resultMin) + resultMin;
    }

}
