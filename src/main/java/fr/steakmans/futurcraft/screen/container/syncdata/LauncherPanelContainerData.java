package fr.steakmans.futurcraft.screen.container.syncdata;

import fr.steakmans.futurcraft.blocks.tileentities.TileEntityBasicPowerUnit;
import fr.steakmans.futurcraft.blocks.tileentities.TileEntityLauncherPanel;
import net.minecraft.world.inventory.SimpleContainerData;

public class LauncherPanelContainerData extends SimpleContainerData {

    private final TileEntityLauncherPanel be;

    public LauncherPanelContainerData(TileEntityLauncherPanel be, int count) {
        super(count);
        this.be = be;
    }

    @Override
    public int get(int key) {
        return switch(key) {
            case 0 -> this.be.energyStorage.getEnergyStored();
            case 1 -> this.be.energyStorage.getMaxEnergyStored();
            case 2 -> this.be.isMissileSlotEmpty();
            case 3 -> this.be.getMissileId();
            case 4 -> this.be.getBlockPos().getX();
            case 5 -> this.be.getBlockPos().getY();
            case 6 -> this.be.getBlockPos().getZ();
            default -> throw new UnsupportedOperationException("There is no value corresponding to: " + key + " in: " + this.be);
        };
    }

}
