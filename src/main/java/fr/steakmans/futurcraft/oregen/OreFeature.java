package fr.steakmans.futurcraft.oregen;

import fr.steakmans.futurcraft.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreFeature {

    public static Holder<PlacedFeature> OVERWORLD_ALUMINUM_GEN;
    public static Holder<PlacedFeature> OVERWORLD_DEEP_ALUMINUM_GEN;
    public static Holder<PlacedFeature> OVERWORLD_SILVER_GEN;
    public static Holder<PlacedFeature> OVERWORLD_DEEP_SILVER_GEN;
    public static Holder<PlacedFeature> OVERWORLD_TITANIUM_GEN;
    public static Holder<PlacedFeature> OVERWORLD_DEEP_TITANIUM_GEN;
    public static Holder<PlacedFeature> OVERWORLD_COBALT_GEN;
    public static Holder<PlacedFeature> OVERWORLD_DEEP_COBALT_GEN;
    public static Holder<PlacedFeature> OVERWORLD_NICKEL_GEN;
    public static Holder<PlacedFeature> OVERWORLD_DEEP_NICKEL_GEN;

    public static void registerOreFeatures() {
        OreConfiguration overworldAluminumConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ALUMINUM_ORE.get().defaultBlockState(), 7);
        OreConfiguration overworldDeepAluminumConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ALUMINUM_ORE.get().defaultBlockState(), 7);
        OreConfiguration overworldSilverConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE.get().defaultBlockState(), 9);
        OreConfiguration overworldDeepSilverConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState(), 7);
        OreConfiguration overworldTitaniumConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TITANIUM_ORE.get().defaultBlockState(), 7);
        OreConfiguration overworldDeepTitaniumConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState(), 7);
        OreConfiguration overworldCobaltConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.COBALT_ORE.get().defaultBlockState(), 3);
        OreConfiguration overworldDeepCobaltConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_COBALT_ORE.get().defaultBlockState(), 4);
        OreConfiguration overworldNickelConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.NICKEL_ORE.get().defaultBlockState(), 3);
        OreConfiguration overworldDeepNickelConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_NICKEL_ORE.get().defaultBlockState(), 4);

        OVERWORLD_ALUMINUM_GEN = registerPlacedOreFeature("overworld_aluminum_ore", new ConfiguredFeature<>(Feature.ORE, overworldAluminumConfig), CountPlacement.of(15), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)));
        OVERWORLD_DEEP_ALUMINUM_GEN = registerPlacedOreFeature("overworld_deepslate_aluminum_ore", new ConfiguredFeature<>(Feature.ORE, overworldDeepAluminumConfig), CountPlacement.of(15), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0)));
        OVERWORLD_SILVER_GEN = registerPlacedOreFeature("overworld_silver_ore", new ConfiguredFeature<>(Feature.ORE, overworldSilverConfig), CountPlacement.of(20), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(70)));
        OVERWORLD_DEEP_SILVER_GEN = registerPlacedOreFeature("overworld_deepslate_silver_ore", new ConfiguredFeature<>(Feature.ORE, overworldDeepSilverConfig), CountPlacement.of(10), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0)));
        OVERWORLD_TITANIUM_GEN = registerPlacedOreFeature("overworld_titanium_ore", new ConfiguredFeature<>(Feature.ORE, overworldTitaniumConfig), CountPlacement.of(10), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(48)));
        OVERWORLD_DEEP_TITANIUM_GEN = registerPlacedOreFeature("overworld_deepslate_titanium_ore", new ConfiguredFeature<>(Feature.ORE, overworldDeepTitaniumConfig), CountPlacement.of(10), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(0)));
        OVERWORLD_COBALT_GEN = registerPlacedOreFeature("overworld_cobalt_ore", new ConfiguredFeature<>(Feature.ORE, overworldCobaltConfig), CountPlacement.of(3), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(16)));
        OVERWORLD_DEEP_COBALT_GEN = registerPlacedOreFeature("overworld_deepslate_cobalt", new ConfiguredFeature<>(Feature.ORE, overworldDeepCobaltConfig), CountPlacement.of(5), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-16)));
        OVERWORLD_NICKEL_GEN = registerPlacedOreFeature("overworld_nickel_ore", new ConfiguredFeature<>(Feature.ORE, overworldNickelConfig), CountPlacement.of(2), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(16)));
        OVERWORLD_DEEP_NICKEL_GEN = registerPlacedOreFeature("overworld_deepslate_nickel_ore", new ConfiguredFeature<>(Feature.ORE, overworldDeepNickelConfig), CountPlacement.of(4), InSquarePlacement.spread(), BiomeFilter.biome(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-32)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedOreFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
        } else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_ALUMINUM_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_DEEP_ALUMINUM_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_SILVER_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_DEEP_SILVER_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_TITANIUM_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_DEEP_TITANIUM_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_COBALT_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_DEEP_COBALT_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_NICKEL_GEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_DEEP_NICKEL_GEN);
        }
    }

}
