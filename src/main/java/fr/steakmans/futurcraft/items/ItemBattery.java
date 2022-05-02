package fr.steakmans.futurcraft.items;

import fr.steakmans.futurcraft.Main;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.StringTagVisitor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ItemBattery extends Item {
    public ItemBattery() {
        super(new Item.Properties().tab(Main.TAB).durability(2000).defaultDurability(2000));
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity p, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, p, p_41407_, p_41408_);

        if(stack.hasTag()) {
            CompoundTag tag = stack.getTag();

            if(!tag.contains("Energy")) {
                tag.putInt("Energy", 0);
            }
            if(!tag.contains("MaxEnergy")) {
                tag.putInt("MaxEnergy", 2000);
            }
            stack.setTag(tag);
        } else {
            CompoundTag tag = new CompoundTag();
            tag.putInt("Energy", 0);
            tag.putInt("MaxEnergy", 2000);
            stack.setTag(tag);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        int energy = tag.getInt("Energy");
        tooltip.add(new TextComponent(energy + "/" + tag.getInt("MaxEnergy") + "FE"));
    }
}
