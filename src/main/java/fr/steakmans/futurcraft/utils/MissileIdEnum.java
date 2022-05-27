package fr.steakmans.futurcraft.utils;

import fr.steakmans.futurcraft.items.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public enum MissileIdEnum {

    BASIC(0, 1, ModItems.BASIC_MISSILE.get()),
    NUCLEAR(1, 3, ModItems.NUCLEAR_MISSILE.get()),
    ANNIHILATION(2, 5, ModItems.ANNIHILATION_MISSILE.get());

    private final int id;
    private final int tier;
    private final Item baseItem;

    MissileIdEnum(int id, int tier, Item item) {
        this.id = id;
        this.tier = tier;
        baseItem = item;
    }

    public int getId() {
        return id;
    }

    public int getTier() {
        return tier;
    }

    public Item getBaseItem() {
        return baseItem;
    }

    @Nullable
    public static MissileIdEnum fromId(int id) {
        for (MissileIdEnum at : MissileIdEnum.values()) {
            if (at.getId() == id) {
                return at;
            }
        }
        return null;
    }

    @Nullable
    public static MissileIdEnum fromItemStack(ItemStack stack) {
        for (MissileIdEnum at : MissileIdEnum.values()) {
            if (stack.is(at.getBaseItem())) {
                return at;
            }
        }
        return null;
    }
}
