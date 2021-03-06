package net.nukesfromthefuture.main;

import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.mojang.datafixers.types.Type;
import com.snowshock35.jeiintegration.JEIIntegration;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ObjectHolder;
import net.nukesfromthefuture.blocks.*;
import net.nukesfromthefuture.containers.*;
import net.nukesfromthefuture.entity.*;
import net.nukesfromthefuture.items.*;
import net.nukesfromthefuture.tabs.*;
import net.nukesfromthefuture.tags.NffTags;
import net.nukesfromthefuture.tileentity.*;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;

@Mod("nff")
public class Nukesfromthefuture {
    //I'm not capitializing variable or field names because I'm rejecting modernity and embracing tradition
    //I might organize this file tho... Nah
    //I probably violated like every naming convention
    public static final String mod_id = "nff";
    public static Item UwU;
    public static Item pizza;
    public static Block trol;
    public static Item fluid_barrel_empty;
    public static Item fluid_barrel_full;
    public static Item fluid_icon;
    //ah, yes, the classic potato
    public static Item POTATO;
    public static Block nether_reactor;
    public static Block nether_reactor_2;
    public static Block nether_reactor_burned_out;
    public static Block red_obsidian;
    public static PositionTrigger rad_trigger;
    public static Block waste;
    public static Block waste_wood;
    public static Block Deathinum_ore;
    public static Item unstable_pluto_identifier;
    public static Item cooked_POTATO;
    public static Block lead_glass;
    public static Block ego_nuke;
    public static Advancement POTATOKill;
    public static Item lead_ingot;
    public static Block egonium_ore;
    //I HATE THE WAY THAT SUBITEMS NOW HAVE TO BE MADE INTO SEPERATE ITEM VARIABLES!!! I missed it when you could just override the getSubItems method
    public static Item empty_identifier;
    public static Item ego_fluid_identifier;
    public static Item ego_ingot;
    public static Block singularity_nuke;
    public static Item energy_extractor;
    public static PositionTrigger installed;
    public static Advancement install_mod;
    public static Item nuke_rod;
    public static Advancement rad_poison;
    public static Advancement tacod;
    public static PositionTrigger sickness;
    public static PositionTrigger rad_death;
    public static PositionTrigger ate_taco;
    public static Item black_hole_tank;
    public static Item black_hole;
    public static Item singularity_magnet;
    public static Item ego_tank;
    public static Item static_donut;
    public static Advancement radiation_death;
    public static Block beta_nuke;
    public static Item nuke_taco;
    //i hate item blocks, item blocks can die
    public static Item iTrol;
    public static Item iLead_glass;
    public static Item iEgo_nuke;
    public static Item iWaste;
    public static Item iNetherReactor;
    public static Item iReactor_2;
    public static Item iReactor_3;
    public static Item iRed_obs;
    public static Item iSingularity_nuke;
    public static Item iWasteWood;
    public static Item iDeathinum;
    public static Item iEgonium_ore;
    public static Item iBeta_nuke;
    //tile entity types.... WHY FORGE!!! WHAT WAS SO WRONG WITH REGISTERING TILE ENTITIES USING THE GAME REGISTRY!?
    @ObjectHolder("nff:ego_thing")
    public static TileEntityType<TileEgoNuke> ego_type;
    @ObjectHolder("nff:beta_thing")
    public static TileEntityType<TileBeta> beta_type;
    @ObjectHolder("nff:collider_tile")
    public static TileEntityType<ColliderTile> collider_tile;
    @ObjectHolder("nff:nether_react_thing")
    public static TileEntityType<TileNReactor> reactor;
    //entity types
    @ObjectHolder("nff:ego_explod")
    public static EntityType<EntityEgoBlast> ego_explod;
    @ObjectHolder("nff:generic_explosion")
    public static EntityType<MK3Explosion> generic;
    @ObjectHolder("nff:potatoent")
    public static EntityType<POTATOEntity> POTATOE;
    @ObjectHolder("nff:blast")
    public static EntityType<Blast> blast;

    public static EntityType<EntityPizzaCreeper> creeper;
    //container types. ik, I'm really unorganized cramming all the contents into the main mod class lol
    @ObjectHolder("nff:ego_container")
    public static ContainerType<EgoContainer> ego_container;
    @ObjectHolder("nff:beta_container")
    public static ContainerType<BetaContainer> beta_container;
    @ObjectHolder("nff:colider_container")
    public static ContainerType<ColliderContainer> collider_container;
    //config values
    public static SpawnEggItem pizza_creep_spawn;
    public static ForgeConfigSpec.BooleanValue render_3d;
    public static ForgeConfigSpec.BooleanValue elevation;
    public static ForgeConfigSpec.IntValue egoNukeSpeed;
    public static ForgeConfigSpec.IntValue egoStrength;
    public static ForgeConfigSpec.IntValue beta_strength;
    public static ForgeConfigSpec.IntValue beta_speed;
    public static ForgeConfigSpec.IntValue mk4;
    public static ForgeConfigSpec.IntValue hellrad;
    public static ForgeConfigSpec.BooleanValue enableRad;
    public static ForgeConfigSpec.IntValue fogRad;
    public static ForgeConfigSpec.IntValue singularity_strength;
    public static ForgeConfigSpec.IntValue singularity_speed;
    public static ForgeConfigSpec.BooleanValue old_ego;
    public static float gui_render_type;
    public static float ego_model_type;
    //logger
    public static Logger logger = LogManager.getLogger();
    //config
    public static ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec config;
    //misc
    public static World getWorld;
    public static ForgeConfigSpec.IntValue cont;
    static{
        egoStrength = builder.defineInRange("ego_nuke_strength", 300, 1, 1000);
        elevation = builder.define("elevation", true);
        egoNukeSpeed = builder.defineInRange("ego_nuke_speed", 9, 1, 40);
        beta_strength = builder.defineInRange("beta_strength", 230, 1, 1000);
        beta_speed = builder.defineInRange("beta_speed", 13, 0, 1000);
        mk4 = builder.defineInRange("mk4", 1024, 0, 10000);
        singularity_strength = builder.defineInRange("singularity_nuke_strngth", 150, 0, 1000);
        singularity_speed = builder.defineInRange("singularity_speed", 13, 0, 1000);
        hellrad = builder.defineInRange("hellrad", 10, 0, 1000);
        fogRad = builder.defineInRange("fograd", 200, 0, 1000);
        enableRad = builder.define("radiation", true);
        cont = builder.defineInRange("cont", 0, 0, 100);
        render_3d = builder.comment("When false, it switches the blocks that have 3d models in the inventory to their classic textures from when the mod was in 1.7.10.").define("render_3d_models_in_gui", true);
        old_ego = builder.comment("it switches between the current model of the ego nuke and the old model of the ego nuke from a very early stage of mod development").define("old_ego_nuke_enabled", false);
        config = builder.build();
    }
    public static void loadConfigStuff(ForgeConfigSpec configThing, String path){
        Nukesfromthefuture.logger.log(Level.INFO, "Loading config file");
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public Nukesfromthefuture(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, config);
        loadConfigStuff(config, FMLPaths.CONFIGDIR.get().resolve("nukesfromthefuture.toml").toString());
        config.save();
        if(render_3d.get()){
            gui_render_type = 0;
        } else if(!render_3d.get()){
            gui_render_type = 1;
        }

        if(!old_ego.get()){
            if(render_3d.get() || !render_3d.get()) {
                ego_model_type = 0;
            }
        } else if(old_ego.get() && render_3d.get()){
            ego_model_type = 1;
        }
        creeper = EntityType.Builder.<EntityPizzaCreeper>create(EntityPizzaCreeper::new, EntityClassification.MONSTER).build("nff:pizza_creeper");
        creeper.setRegistryName("pizza_creeper");
        UwU = new Item(new Item.Properties().group(stuff)).setRegistryName("owo");
        trol = new TrollBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.3F, 0F)).setRegistryName("trol");
        iTrol = new BlockItem(trol, new Item.Properties().group(stuff)).setRegistryName("trol");
        pizza = new Pizza(new Item.Properties().food(new Food.Builder().hunger(20).saturation(10F).meat().build()).group(food)).setRegistryName("pizza");
        POTATO = new POTATO(new Item.Properties().group(weapons).isImmuneToFire()).setRegistryName("potatoe");
        cooked_POTATO = new CookedPotato(new Item.Properties().group(food).food(new Food.Builder().hunger(20).saturation(10F).build())).setRegistryName("cooked_potatoe");
        lead_glass = new LeadGlass(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(5.0F, 6.0F).notSolid().noDrops()).setRegistryName("lead_glass");
        iLead_glass = new BlockItem(lead_glass, new Item.Properties().group(stuff)).setRegistryName("lead_glass");
        ego_nuke = new EgoNuke(Block.Properties.create(Material.IRON).notSolid().hardnessAndResistance(3.0F, 1F).sound(SoundType.ANVIL)).setRegistryName("ego_nuke");
        iEgo_nuke = new BlockItem(ego_nuke, new Item.Properties().group(weapons)).setRegistryName("ego_nuke");
        lead_ingot = new Lead_Ingot(new Item.Properties().group(resources)).setRegistryName("lead_ingot");
        egonium_ore = new EgoniumOre(Block.Properties.create(Material.ROCK).hardnessAndResistance(6.0F, 2.0F).sound(SoundType.STONE)).setRegistryName("ego_ore");
        iEgonium_ore = new BlockItem(egonium_ore, new Item.Properties().group(resources)).setRegistryName("ego_ore");
        ego_ingot = new EgoIngot(new Item.Properties().group(resources)).setRegistryName("ego_ingot");
        static_donut = new Donut(new Item.Properties().group(food).food(new Food.Builder().hunger(6).saturation(2F).build())).setRegistryName("static_donut");
        Deathinum_ore = new Deathinum_Ore(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(10.0F, 5.0F).setRequiresTool()).setRegistryName("deathinum_ore");
        iDeathinum = new BlockItem(Deathinum_ore, new Item.Properties().group(resources)).setRegistryName("deathinum_ore");
        waste = new Waste(Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.GROUND).hardnessAndResistance(0.2F, 10.F)).setRegistryName("waste");
        iWaste = new BlockItem(waste, new Item.Properties().group(resources)).setRegistryName("waste");
        waste_wood = new Waste_wood(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.4F, 10F)).setRegistryName("waste_wood");
        iWasteWood = new BlockItem(waste_wood, new Item.Properties().group(resources)).setRegistryName("waste_wood");
        beta_nuke = new Beta(Block.Properties.create(Material.IRON).notSolid().hardnessAndResistance(2.0F, 10.0F).sound(SoundType.METAL)).setRegistryName("beta");
        iBeta_nuke = new BlockItem(beta_nuke, new Item.Properties().group(weapons)).setRegistryName("beta");
        ego_fluid_identifier = new ItemFluidIdentidier(new Item.Properties().group(resources)).setRegistryName("ego_fluid_identifier");
        //empty_identifier = new ItemFluidIdentidier(new Item.Properties().group(resources)).setRegistryName("empty_identifier");
        installed = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation(mod_id, "installed_mod")));
        install_mod = Advancement.Builder.builder().withDisplay(new ItemStack(iEgo_nuke), new StringTextComponent("nff_installed"), new StringTextComponent("tanks_for_installing"), new ResourceLocation(mod_id, "textures/blocks/ego_ore.png"), FrameType.TASK, true, true, false).withCriterion("install", new PositionTrigger.Instance(installed.getId(), EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY)).register(Advancement::getCriteria, "nff:things/root");
        //unstable_pluto_identifier = new ItemFluidIdentidier(new Item.Properties().group(resources), FluidHandler.FluidType.unstable_plutonium).setRegistryName("unstable_identifier");
        //ego_tank = new FluidTankItem(FluidHandler.FluidType.egonium, new Item.Properties().group(resources)).setRegistryName("ego_tank");
        //black_hole_tank = new FluidTankItem(FluidHandler.FluidType.BLACK_HOLE_FUEL, new Item.Properties().group(resources)).setRegistryName("black_hole_tank");
        singularity_nuke = new SingularityNuke(Block.Properties.create(Material.IRON).sound(SoundType.ANVIL).hardnessAndResistance(2.0F, 3.0F).notSolid()).setRegistryName("singularity_nuke");
        iSingularity_nuke = new BlockItem(singularity_nuke, new Item.Properties().group(weapons)).setRegistryName("singularity_nuke");
        fluid_barrel_empty = new Item(new Item.Properties().group(resources)).setRegistryName("empty_tank");
        fluid_barrel_full = new FluidTankItem(new Item.Properties().group(machines)).setRegistryName("fluid_tank_full");
        black_hole = new Item(new Item.Properties().group(weapons)).setRegistryName("black_hole");
        ate_taco = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation(mod_id, "ate_taco")));
        singularity_magnet = new Item(new Item.Properties().group(machines)).setRegistryName("sing_magnet");
        nether_reactor = new NetherReact(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5.0F)).setRegistryName("nether_reactor");
        nether_reactor_2 = new NetherReact(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1)).setRegistryName("nether_two");
        nether_reactor_burned_out = new Block(AbstractBlock.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(-1).sound(SoundType.BASALT)).setRegistryName("burned_out");
        red_obsidian = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(-1).setLightLevel((state) -> {return 15;})).setRegistryName("red_obsidian");
        iRed_obs = new BlockItem(red_obsidian, new Item.Properties().group(resources)).setRegistryName("red_obsidian");
        iNetherReactor = new BlockItem(nether_reactor, new Item.Properties().group(machines)).setRegistryName("nether_reactor");
        iReactor_2 = new BlockItem(nether_reactor_2, new Item.Properties()).setRegistryName("nether_two");
        iReactor_3 = new BlockItem(nether_reactor_burned_out, new Item.Properties()).setRegistryName("burned_out");
        rad_trigger = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation(mod_id, "throw_potato")));
        sickness = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation(mod_id, "sickness")));
        rad_death = CriteriaTriggers.register(new PositionTrigger(new ResourceLocation(mod_id, "radiation_death")));
        nuke_taco = new NukeTaco(new Item.Properties().group(food).food(new Food.Builder().meat().hunger(5).saturation(10).build())).setRegistryName("nuke_taco");
        pizza_creep_spawn = (SpawnEggItem) new SpawnEggItem(creeper, 0xDA0000, 0x009300, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("creeper_spawn");
        //FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(black_hole_tank), new ItemStack(fluid_barrel_empty), FluidHandler.FluidType.BLACK_HOLE_FUEL, 16000));
        POTATOKill = Advancement.Builder.builder().withDisplay(new ItemStack(POTATO), new StringTextComponent("POTATO_kill"), new StringTextComponent("Kill_with_da_potato"), null, FrameType.CHALLENGE, true, true, false).withCriterion("potato_kill", new PositionTrigger.Instance(rad_trigger.getId(), EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY)).register(Advancement::getCriteria, "nff:things/potato_kill");
        rad_poison = Advancement.Builder.builder().withDisplay(new ItemStack(iReactor_2), new StringTextComponent("rad_sickness"), new StringTextComponent("get_rad_sickness"), null, FrameType.TASK, true, true, false).withCriterion("rad_cancer_UwU", new PositionTrigger.Instance(sickness.getId(), EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY)).register(Advancement::getCriteria, "nff:things/radiation_poisoning");
        radiation_death = Advancement.Builder.builder().withDisplay(new ItemStack(iReactor_3), new StringTextComponent("radiation_death"), new StringTextComponent("die_from_radiation"), null, FrameType.CHALLENGE, true, true, true).withCriterion("rad_death_oof", new PositionTrigger.Instance(rad_death.getId(), EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY)).register(Advancement::getCriteria, "nff:things/rad_death");
        tacod = Advancement.Builder.builder().withDisplay(new ItemStack(nuke_taco), new StringTextComponent("tacod"), new StringTextComponent("tacod"), null, FrameType.TASK, true, true, true).withCriterion("taco_bell", new PositionTrigger.Instance(ate_taco.getId(), EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY)).register(Advancement::getCriteria, "nff:things/tacod");
        MinecraftForge.EVENT_BUS.register(new ModEventHandler.Forge_bus());
        NffTags.register();
    }
    public static ItemGroup stuff = new UselessTab("uselessStuff");
    public static ItemGroup weapons = new Weapons("nffweapons");
    public static ItemGroup resources = new ResourceTab("nffresources");
    public static ItemGroup machines = new MachineTab("nffMachines");
    public static ItemGroup food = new FoodTab("foods");

}
