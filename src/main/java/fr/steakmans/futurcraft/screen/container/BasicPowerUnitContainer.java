package fr.steakmans.futurcraft.screen.container;

import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityBasicPowerUnit;
import fr.steakmans.futurcraft.screen.container.syncdata.BasicPowerUnitContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BasicPowerUnitContainer extends AbstractContainerMenu {

    private final ContainerLevelAccess containerAcces;
    public final ContainerData data;

    //client
    protected BasicPowerUnitContainer(int id, Inventory playerInv) {
        this(id, playerInv, new ItemStackHandler(7), BlockPos.ZERO, new SimpleContainerData(4));
    }

    //server
    public BasicPowerUnitContainer(int id, Inventory playerInv, IItemHandler slots, BlockPos pos, ContainerData data) {
        super(ModContainers.BASIC_POWER_UNIT.get(), id);
        this.containerAcces = ContainerLevelAccess.create(playerInv.player.level, pos);
        this.data = data;

        final int slotSizePlus2 = 18, startX = 8, startY = 84, hotbarY = 142;

        addSlot(new SlotItemHandler(slots, 0, 53, 34));
        addSlot(new SlotItemHandler(slots, 1, 108, 34));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++){
                addSlot(new Slot(playerInv, 9 + row * 9 + column, startX + column * slotSizePlus2, startY + row * slotSizePlus2));
            }
        }

        for(int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY));
        }

        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(containerAcces, player, ModBlocks.BASIC_POWER_UNIT.get());
    }

    public static MenuConstructor getServerContainer(TileEntityBasicPowerUnit be, BlockPos pos) {
        return (id, playerInv, player) -> new BasicPowerUnitContainer(id, playerInv, be.inventory, pos, new BasicPowerUnitContainerData(be, 2));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = getSlot(index);

        if(slot.hasItem()) {
            final ItemStack item = slot.getItem();
            retStack = item.copy();
            if(index < 1) {
                if(!moveItemStackTo(item, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(item, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            if(item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else slot.setChanged();
        }

        return retStack;
    }

}
