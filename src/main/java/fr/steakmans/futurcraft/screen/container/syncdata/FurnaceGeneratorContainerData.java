package fr.steakmans.futurcraft.screen.container.syncdata;

import fr.steakmans.futurcraft.blocks.tileentities.TileEntityFurnaceGenerator;
import net.minecraft.world.inventory.SimpleContainerData;

public class FurnaceGeneratorContainerData extends SimpleContainerData {

    private final TileEntityFurnaceGenerator be;

    public FurnaceGeneratorContainerData(TileEntityFurnaceGenerator be, int count) {
        super(count);
        this.be = be;
    }

    @Override
    public int get(int key) {
        return switch(key) {
          case 0 -> this.be.getProgress();
          case 1 -> this.be.getMaxProgress();
          case 2 -> this.be.energyStorage.getEnergyStored();
          case 3 -> this.be.energyStorage.getMaxEnergyStored();
            default -> throw new UnsupportedOperationException("There is no value corresponding to: " + key + " in: " + this.be);
        };
    }



}
