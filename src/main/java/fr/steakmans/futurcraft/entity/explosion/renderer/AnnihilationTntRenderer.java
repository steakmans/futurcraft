package fr.steakmans.futurcraft.entity.explosion.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.entity.explosion.AnnihilationPrimedTnt;
import fr.steakmans.futurcraft.entity.explosion.NuclearPrimedTnt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnnihilationTntRenderer<Type extends AnnihilationPrimedTnt> extends EntityRenderer<AnnihilationPrimedTnt> {


    public AnnihilationTntRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(AnnihilationPrimedTnt p_116177_, float p_116178_, float p_116179_, PoseStack p_116180_, MultiBufferSource p_116181_, int p_116182_) {
        p_116180_.pushPose();
        p_116180_.translate(0.0D, 0.5D, 0.0D);
        int i = p_116177_.getFuse();
        if ((float)i - p_116179_ + 1.0F < 10.0F) {
            float f = 1.0F - ((float)i - p_116179_ + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            p_116180_.scale(f1, f1, f1);
        }

        p_116180_.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        p_116180_.translate(-0.5D, -0.5D, 0.5D);
        p_116180_.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(ModBlocks.NUCLEAR_TNT.get().defaultBlockState(), p_116180_, p_116181_, p_116182_, i / 5 % 2 == 0);
        p_116180_.popPose();
        super.render(p_116177_, p_116178_, p_116179_, p_116180_, p_116181_, p_116182_);
    }

    @Override
    public ResourceLocation getTextureLocation(AnnihilationPrimedTnt p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
