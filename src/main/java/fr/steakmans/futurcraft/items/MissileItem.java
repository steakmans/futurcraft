package fr.steakmans.futurcraft.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MissileItem extends Item {

    public MissileItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity p, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, p, p_41407_, p_41408_);

        CompoundTag tag = stack.getOrCreateTag();

        if(!tag.contains("missile")) tag.putInt("missile", 0);
    }
}
