package fr.steakmans.futurcraft.items;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.entity.ModEntities;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryGrenade;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class IncendiaryGreandeItem extends Item {
    public IncendiaryGreandeItem() {
        super(new Item.Properties().tab(Main.TAB).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player p, InteractionHand hand) {
        Vec3 rot = new Vec3(p.getLookAngle().x, 0 , p.getLookAngle().z);
        IncendiaryGrenade grenade = new IncendiaryGrenade(ModEntities.INCENDIARY_GRENADE.get(), level, p.getX(), p.getY() + 1.5f, p.getZ(),
                null, rot.x, -p.getXRot(), rot.z);
        level.addFreshEntity(grenade);
        return super.use(level, p, hand);
    }
}
