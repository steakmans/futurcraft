package fr.steakmans.futurcraft.blocks.tileentities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityLauncherPanel;
import fr.steakmans.futurcraft.items.ModItems;
import fr.steakmans.futurcraft.utils.MissileIdEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nullable;

public class LauncherPanelBER implements BlockEntityRenderer<TileEntityLauncherPanel> {

    private int missileId;

    private final BlockEntityRendererProvider.Context context;

    public LauncherPanelBER(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(TileEntityLauncherPanel be, float partialTicks, PoseStack stack, MultiBufferSource buf, int packedLight, int combinedOverlay) {
        final ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        missileId = be.getMissileId();
        Minecraft.getInstance().player.displayClientMessage(new TranslatableComponent("=" + missileId), true);
        if (missileId != -1) {
                //renderer.renderStatic(itemStack, ItemTransforms.TransformType.HEAD, packedLight, OverlayTexture.NO_OVERLAY, stack, buf, 0);
        }
    }

}
