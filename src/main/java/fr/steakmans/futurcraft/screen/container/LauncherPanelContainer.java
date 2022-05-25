package fr.steakmans.futurcraft.screen.container;

import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityLauncherPanel;
import fr.steakmans.futurcraft.screen.container.syncdata.BasicPowerUnitContainerData;
import fr.steakmans.futurcraft.screen.container.syncdata.LauncherPanelContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LauncherPanelContainer extends AbstractContainerMenu {

    private final ContainerLevelAccess containerAcces;
    public final ContainerData data;

    //client
    protected LauncherPanelContainer(int id, Inventory playerInv) {
        this(id, playerInv, new ItemStackHandler(7), BlockPos.ZERO, new SimpleContainerData(7));
    }

    //server
    public LauncherPanelContainer(int id, Inventory playerInv, IItemHandler slots, BlockPos pos, ContainerData data) {
        super(ModContainers.LAUNCHER_PANEL.get(), id);
        this.containerAcces = ContainerLevelAccess.create(playerInv.player.level, pos);
        this.data = data;

        final int slotSizePlus2 = 18, startX = 8, startY = 124, hotbarY = 182;

        addSlot(new SlotItemHandler(slots, 0, 53, 80));
        addSlot(new SlotItemHandler(slots, 1, 108, 80));

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
        return stillValid(containerAcces, player, ModBlocks.LAUNCHER_PANEL.get());
    }

    public static MenuConstructor getServerContainer(TileEntityLauncherPanel be, BlockPos pos) {
        return (id, playerInv, player) -> new LauncherPanelContainer(id, playerInv, be.inventory, pos, new LauncherPanelContainerData(be, 7));
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
