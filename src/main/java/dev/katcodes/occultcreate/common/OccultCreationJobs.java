package dev.katcodes.occultcreate.common;

import com.klikli_dev.occultism.Occultism;
import com.klikli_dev.occultism.client.entities.SpiritJobClient;
import com.klikli_dev.occultism.common.entity.job.SpiritJobFactory;
import com.klikli_dev.occultism.registry.OccultismSpiritJobs;
import dev.katcodes.occultcreate.OccultCreate;
import dev.katcodes.occultcreate.common.crank.TurnCrankJob;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OccultCreationJobs {

    public static DeferredRegister<SpiritJobFactory> JOBS = DeferredRegister.create(new ResourceLocation(Occultism.MODID, "spirit_job_factory"), OccultCreate.MODID);



    public static final RegistryObject<SpiritJobFactory> CRANKER = JOBS.register("cranker",() -> new SpiritJobFactory(TurnCrankJob::new, SpiritJobClient.create(new ResourceLocation(OccultCreate.MODID,"cranker"))));

    public static void  init() {
        JOBS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
