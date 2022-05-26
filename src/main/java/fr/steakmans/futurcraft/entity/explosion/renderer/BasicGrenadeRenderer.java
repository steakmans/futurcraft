package fr.steakmans.futurcraft.entity.explosion.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.entity.explosion.BasicGrenade;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryGrenade;
import fr.steakmans.futurcraft.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BasicGrenadeRenderer<Type extends BasicGrenade> extends EntityRenderer<BasicGrenade> {

    private int x = 0;

    public BasicGrenadeRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(BasicGrenade grenade, float p_116178_, float p_116179_, PoseStack stack, MultiBufferSource buf, int p_116182_) {
        stack.pushPose();
        stack.translate(0, 0.3, 0);
        if(!grenade.isOnGround()) {
            stack.mulPose(Vector3f.XN.rotationDegrees(5 * x++));
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(ModItems.BASIC_GRENADE.get().getDefaultInstance(), ItemTransforms.TransformType.HEAD, 0, this.getBlockLightLevel(grenade, grenade.getOnPos()), stack, buf, 0);
        stack.popPose();
        super.render(grenade, p_116178_, p_116179_, stack, buf, p_116182_);
    }

    @Override
    public ResourceLocation getTextureLocation(BasicGrenade p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
