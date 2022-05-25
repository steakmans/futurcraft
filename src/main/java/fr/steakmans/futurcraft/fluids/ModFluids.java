package fr.steakmans.futurcraft.fluids;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.blocks.ModBlocks;
import fr.steakmans.futurcraft.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Main.MODID);

    //textures
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    //fluids
    public static final RegistryObject<FlowingFluid> PETROL_FLUID = FLUIDS.register("petrol_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.PETROL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> PETROL_FLOWING = FLUIDS.register("petrol_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.PETROL_PROPERTIES));

    //properties
    public static final ForgeFlowingFluid.Properties PETROL_PROPERTIES = new ForgeFlowingFluid.Properties(() ->
            PETROL_FLUID.get(), () -> PETROL_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(15).luminosity(0).viscosity(5).overlay(WATER_OVERLAY_RL).color(0xA9A9A9)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> ModFluids.PETROL_BLOCK.get()).bucket(() -> ModItems.PETROL_BUCKET.get());

    //blocks
    public static final RegistryObject<LiquidBlock> PETROL_BLOCK = ModBlocks.BLOCKS.register("petrol", () -> new LiquidBlock(() ->
            ModFluids.PETROL_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

}
