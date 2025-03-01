package dev.katcodes.occultcreate.common.datagen;

import dev.katcodes.occultcreate.OccultCreate;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ENLanguageProvider extends LanguageProvider {

    public ENLanguageProvider(PackOutput output) {
        super(output, OccultCreate.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("create.occult_creations.recipe.spirit_fire_blasting","Bulk Spirit Processing");
        this.add("create.recipe.spirit_fire_blasting.fan","Fan behind Spirit Flame");
    }
}
