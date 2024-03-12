package dev.katcodes.occultcreate.common.items;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.item.DummyTooltipItem;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.katcodes.occultcreate.OccultCreate;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.klikli_dev.occultism.registry.OccultismItems.defaultProperties;

public class ModItems {


    public static final RegistryEntry<BookOfCallingCranker> BOOK_OF_CALLING_FOLIOT_CRANKER = OccultCreate.REGISTRATE.get()
            .item("book_of_calling_foliot_cranker",BookOfCallingCranker::new)
            .defaultLang()
            .defaultModel()
//            .initialProperties(() ->)
            .register();

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OccultCreate.MODID);


    //Ritual Dummy Items
    static {
                ITEMS.register("ritual_dummy/summon_foliot_cranker", () -> new DummyTooltipItem(defaultProperties()));

        //
    }
}
