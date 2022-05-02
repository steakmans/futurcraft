package fr.steakmans.futurcraft.screen.container;

import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityFurnaceGenerator;
import fr.steakmans.futurcraft.screen.container.syncdata.FurnaceGeneratorContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FurnaceGeneratorContainer extends AbstractContainerMenu {

    private final ContainerLevelAccess containerAcces;
    public final ContainerData data;

    //client
    protected FurnaceGeneratorContainer(int id, Inventory playerInv) {
        this(id, playerInv, new ItemStackHandler(7), BlockPos.ZERO, new SimpleContainerData(4));
    }

    //server
    public FurnaceGeneratorContainer(int id, Inventory playerInv, IItemHandler slots, BlockPos pos, ContainerData data) {
        super(ModContainers.FURNACE_GENERATOR.get(), id);
        this.containerAcces = ContainerLevelAccess.create(playerInv.player.level, pos);
        this.data = data;

        final int slotSizePlus2 = 18, startX = 8, startY = 84, hotbarY = 142;

        addSlot(new SlotItemHandler(slots, 0, 38, 35));
        addSlot(new SlotItemHandler(slots, 1, 101, 54));

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
        return stillValid(containerAcces, player, ModBlocks.FURNACE_GENERATOR.get());
    }

    public static MenuConstructor getServerContainer(TileEntityFurnaceGenerator be, BlockPos pos) {
        return (id, playerInv, player) -> new FurnaceGeneratorContainer(id, playerInv, be.inventory, pos, new FurnaceGeneratorContainerData(be, 4));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = getSlot(index);

        /*if(slot.hasItem()) {
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
         */

        return retStack;
    }

}
