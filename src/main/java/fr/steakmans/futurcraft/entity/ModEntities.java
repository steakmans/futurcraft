package fr.steakmans.futurcraft.entity;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.entity.explosion.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    public static final RegistryObject<EntityType<IncendiaryPrimedTnt>> PRIMED_INCENDIARY_TNT = ENTITIES.register("primed_incendiary_tnt", () -> EntityType.Builder.<IncendiaryPrimedTnt>of(IncendiaryPrimedTnt::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "incendiary_primed_tnt").toString()));
    public static final RegistryObject<EntityType<NuclearPrimedTnt>> NUCLEAR_PRIMED_TNT = ENTITIES.register("nuclear_primed_tnt", () -> EntityType.Builder.<NuclearPrimedTnt>of(NuclearPrimedTnt::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "nuclear_primed_tnt").toString()));
    public static final RegistryObject<EntityType<AnnihilationPrimedTnt>> ANNIHILATION_PRIMED_TNT = ENTITIES.register("annihilation_primed_tnt", () -> EntityType.Builder.<AnnihilationPrimedTnt>of(AnnihilationPrimedTnt::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "annihilation_primed_tnt").toString()));

    public static final RegistryObject<EntityType<BasicGrenade>> BASIC_GRENADE = ENTITIES.register("basic_grenade", () -> EntityType.Builder.<BasicGrenade>of(BasicGrenade::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "basic_grenade").toString()));
    public static final RegistryObject<EntityType<IncendiaryGrenade>> INCENDIARY_GRENADE = ENTITIES.register("incendiary_grenade", () -> EntityType.Builder.<IncendiaryGrenade>of(IncendiaryGrenade::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "incendiary_grenade").toString()));

    public static final RegistryObject<EntityType<BasicMissileEntity>> BASIC_MISSILE = ENTITIES.register("basic_missile", () -> EntityType.Builder.<BasicMissileEntity>of(BasicMissileEntity::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "basic_missile").toString()));
    public static final RegistryObject<EntityType<NuclearMissileEntity>> NUCLEAR_MISSILE = ENTITIES.register("nuclear_missile", () -> EntityType.Builder.<NuclearMissileEntity>of(NuclearMissileEntity::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "nuclear_missile").toString()));
    public static final RegistryObject<EntityType<AnnihilationMissileEntity>> ANNIHILATION_MISSILE = ENTITIES.register("annihilation_missile", () -> EntityType.Builder.<AnnihilationMissileEntity>of(AnnihilationMissileEntity::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "annihilation_missile").toString()));
}
