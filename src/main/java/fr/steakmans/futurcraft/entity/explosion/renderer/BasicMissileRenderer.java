package fr.steakmans.futurcraft.entity.explosion.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.entity.BasicMissileEntity;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryPrimedTnt;
import fr.steakmans.futurcraft.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BasicMissileRenderer<Type extends BasicMissileEntity> extends EntityRenderer<BasicMissileEntity> {


    public BasicMissileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(BasicMissileEntity entity, float p_116178_, float p_116179_, PoseStack stack, MultiBufferSource buf, int p_116182_) {
        BlockPos pos = new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
        Minecraft.getInstance().getItemRenderer().renderStatic(ModItems.BASIC_MISSILE.get().getDefaultInstance(), ItemTransforms.TransformType.HEAD, this.getBlockLightLevel(entity, pos), 0, stack, buf, 0);
        super.render(entity, p_116178_, p_116179_, stack, buf, p_116182_);
    }

    @Override
    public ResourceLocation getTextureLocation(BasicMissileEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}