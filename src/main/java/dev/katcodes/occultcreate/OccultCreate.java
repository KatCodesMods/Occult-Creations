package dev.katcodes.occultcreate;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingTypeRegistry;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.katcodes.occultcreate.common.Config;
import dev.katcodes.occultcreate.common.OccultCreationJobs;
import dev.katcodes.occultcreate.common.OccultCreationLang;
import dev.katcodes.occultcreate.common.items.BookOfCallingCranker;
import dev.katcodes.occultcreate.common.items.ModItems;
import dev.katcodes.occultcreate.common.items.itemMode.ItemModes;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import dev.katcodes.occultcreate.common.ProcessingTypes.SpiritProcessing;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(OccultCreate.MODID)
public class OccultCreate
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "occultcreate";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    public OccultCreate()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BookOfCallingCranker.class);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        OccultCreationJobs.init();
        ModItems.init();
        OccultCreationLang.init();
        ItemModes.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        FanProcessingTypeRegistry.register(new ResourceLocation(MODID,"spirit_fire_blasting"),new SpiritProcessing());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }
}
