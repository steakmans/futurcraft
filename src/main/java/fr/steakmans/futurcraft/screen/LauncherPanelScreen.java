package fr.steakmans.futurcraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.packets.ModPackets;
import fr.steakmans.futurcraft.packets.SpawnMissilePacket;
import fr.steakmans.futurcraft.screen.container.BasicPowerUnitContainer;
import fr.steakmans.futurcraft.screen.container.LauncherPanelContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class LauncherPanelScreen extends AbstractContainerScreen<LauncherPanelContainer> {

    //public static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "/textures/gui/container/basic_power_unit.png");

    private ExtendedButton btn;
    private EditBox xPos;
    private EditBox yPos;
    private EditBox zPos;
    private EditBox lockHeight;

    public LauncherPanelScreen(LauncherPanelContainer container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 250;
    }

    @Override
    protected void renderBg(PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        /*renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);*/
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        /*final int scaledEnergy = (int) mapNumber(this.menu.data.get(0), 0, this.menu.data.get(1), 0, 54);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(stack, this.leftPos + 72, this.topPos + 70 - scaledEnergy, 176, 54 - scaledEnergy, 208 - 175, scaledEnergy);
        */
        //font.draw(stack, playerInventoryTitle, this.leftPos + 8, this.topPos + 74, 0x404040);
        //font.draw(stack, title, this.leftPos + 8, this.topPos + 5, 0x404040);
        xPos.render(stack, mouseX, mouseY, partialTicks);
        yPos.render(stack, mouseX, mouseY, partialTicks);
        zPos.render(stack, mouseX, mouseY, partialTicks);
        lockHeight.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }


    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        xPos = new EditBox(font, leftPos + 4, topPos + 16, (imageWidth - 8) / 2 , 25, new TranslatableComponent("Position X"));
        xPos.setMaxLength(8);
        yPos = new EditBox(font, leftPos + 4, topPos + 16 + 25 + 5, (imageWidth - 8) / 2, 25, new TranslatableComponent("Position Y"));
        yPos.setMaxLength(4);
        zPos = new EditBox(font, leftPos + imageWidth - (imageWidth - 8) / 2, topPos + 16, (imageWidth - 8) / 2 , 25, new TranslatableComponent("Position X"));
        zPos.setMaxLength(8);
        lockHeight = new EditBox(font, leftPos + imageWidth - (imageWidth - 8) / 2, topPos + 16 + 25 + 5, (imageWidth - 8) / 2, 25, new TranslatableComponent("Position Y"));
        lockHeight.setMaxLength(4);
        lockHeight.setValue("400");
        this.addWidget(this.yPos);
        this.addWidget(this.xPos);
        this.addWidget(this.zPos);
        this.addWidget(this.lockHeight);
        btn = this.addRenderableWidget(new ExtendedButton(leftPos + imageWidth / 2 - 25, topPos + 100, 50, 15, new TranslatableComponent("launch"), btn -> {
            //Minecraft.getInstance().player.displayClientMessage(new TextComponent("sdfsf"), false);
            int targetX = Integer.parseInt(xPos.getValue());
            int targetY = Integer.parseInt(yPos.getValue());
            int targetZ = Integer.parseInt(zPos.getValue());
            int lock = Integer.parseInt(lockHeight.getValue());
            if(lock < 100) lock = 100;
            ModPackets.NETWORK.sendToServer(new SpawnMissilePacket(new BlockPos(menu.data.get(4), menu.data.get(5), menu.data.get(6)), targetX, targetY, targetZ, lock));
        }));
    }

    @Override
    public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
        String x = this.xPos.getValue();
        String y = this.yPos.getValue();
        String z = this.zPos.getValue();
        String lock = this.lockHeight.getValue();
        super.resize(p_96575_, p_96576_, p_96577_);
        this.xPos.setValue(x);
        this.yPos.setValue(y);
        this.zPos.setValue(z);
        this.lockHeight.setValue(lock);
    }

    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }
    @Override
    protected void containerTick() {
        super.containerTick();
        xPos.tick();
        yPos.tick();
        zPos.tick();
        lockHeight.tick();
        if(yPos.getValue().isEmpty()) yPos.setSuggestion("Detonation Height");
        else yPos.setSuggestion("");
        if(xPos.getValue().isEmpty()) xPos.setSuggestion("Detonation X");
        else xPos.setSuggestion("");
        if(zPos.getValue().isEmpty()) zPos.setSuggestion("Detonation Z");
        else zPos.setSuggestion("");
        if(lockHeight.getValue().isEmpty()) lockHeight.setSuggestion("Lock Height");
        else lockHeight.setSuggestion("");
        btn.active = true;
        if(this.menu.data.get(2) != 1 || this.menu.data.get(3) == -1 ||
        !isInteger(xPos.getValue()) || !isInteger(yPos.getValue()) || !isInteger(zPos.getValue()) ||
                !isInteger(lockHeight.getValue())) {
            btn.active = false;
        }

    }

    public static double mapNumber(double value, double rangeMin, double rangeMax, double resultMin, double resultMax) {
        return (value - rangeMin) / (rangeMax - rangeMin) * (resultMax - resultMin) + resultMin;
    }

    public static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        try {
            int x = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
