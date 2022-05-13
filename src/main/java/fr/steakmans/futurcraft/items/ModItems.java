package fr.steakmans.futurcraft.items;

import fr.steakmans.futurcraft.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    //ores
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register("nickel_ingot", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> RAW_ALUMINUM = ITEMS.register("raw_aluminum", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> RAW_SIVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> RAW_NICKEL = ITEMS.register("raw_nickel", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> RADIUM = ITEMS.register("radium", () -> new Item(new Item.Properties().tab(Main.TAB)));
    public static final RegistryObject<Item> TREATED_RADIUM = ITEMS.register("treated_radium", () -> new Item(new Item.Properties().tab(Main.TAB)));

    //machine component
    public static final RegistryObject<Item> BATTERY = ITEMS.register("battery", ItemBattery::new);

    //grenades
    public static final RegistryObject<Item> INCENDIARY_GRENADE = ITEMS.register("incendiary_greanade", IncendiaryGreandeItem::new);
    public static final RegistryObject<Item> BASIC_GRENADE = ITEMS.register("basic_grenade", BasicGreandeItem::new);
}
