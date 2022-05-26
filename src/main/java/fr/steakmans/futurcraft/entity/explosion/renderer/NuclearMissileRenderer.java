package fr.steakmans.futurcraft.entity.explosion.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.steakmans.futurcraft.entity.explosion.BasicMissileEntity;
import fr.steakmans.futurcraft.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NuclearMissileRenderer<Type extends BasicMissileEntity> extends EntityRenderer<BasicMissileEntity> {


    public NuclearMissileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(BasicMissileEntity entity, float p_116178_, float p_116179_, PoseStack stack, MultiBufferSource buf, int p_116182_) {
        BlockPos pos = new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
        Minecraft.getInstance().getItemRenderer().renderStatic(ModItems.NUCLEAR_MISSILE.get().getDefaultInstance(), ItemTransforms.TransformType.HEAD, 0, this.getBlockLightLevel(entity, pos), stack, buf, 0);
        super.render(entity, p_116178_, p_116179_, stack, buf, getBlockLightLevel(entity, entity.getOnPos()));
    }

    @Override
    public ResourceLocation getTextureLocation(BasicMissileEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
