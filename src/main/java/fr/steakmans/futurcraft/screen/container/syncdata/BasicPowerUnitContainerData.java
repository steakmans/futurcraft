package fr.steakmans.futurcraft.screen.container.syncdata;

import fr.steakmans.futurcraft.blocks.tileentities.TileEntityBasicPowerUnit;
import net.minecraft.world.inventory.SimpleContainerData;

public class BasicPowerUnitContainerData extends SimpleContainerData {

    private final TileEntityBasicPowerUnit be;

    public BasicPowerUnitContainerData(TileEntityBasicPowerUnit be, int count) {
        super(count);
        this.be = be;
    }

    @Override
    public int get(int key) {
        return switch(key) {
            case 0 -> this.be.energyStorage.getEnergyStored();
            case 1 -> this.be.energyStorage.getMaxEnergyStored();
            default -> throw new UnsupportedOperationException("There is no value corresponding to: " + key + " in: " + this.be);
        };
    }

}
