package fr.steakmans.futurcraft.entity;

import fr.steakmans.futurcraft.Main;
import fr.steakmans.futurcraft.entity.explosion.BasicGrenade;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryGrenade;
import fr.steakmans.futurcraft.entity.explosion.IncendiaryPrimedTnt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    public static final RegistryObject<EntityType<IncendiaryPrimedTnt>> PRIMED_INCENDIARY_TNT = ENTITIES.register("primed_incendiary_tnt", () -> EntityType.Builder.<IncendiaryPrimedTnt>of(IncendiaryPrimedTnt::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "incendiary_primed_tnt").toString()));
    public static final RegistryObject<EntityType<IncendiaryGrenade>> INCENDIARY_GRENADE = ENTITIES.register("incendiary_grenade", () -> EntityType.Builder.<IncendiaryGrenade>of(IncendiaryGrenade::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "incendiary_grenade").toString()));
    public static final RegistryObject<EntityType<BasicGrenade>> BASIC_GRENADE = ENTITIES.register("basic_grenade", () -> EntityType.Builder.<BasicGrenade>of(BasicGrenade::new, MobCategory.MISC).build(new ResourceLocation(Main.MODID, "basic_grenade").toString()));

}