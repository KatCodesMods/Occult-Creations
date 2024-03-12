package dev.katcodes.occultcreate.common;

import dev.katcodes.occultcreate.OccultCreate;

public class OccultCreationLang {

    public static void init() {
        OccultCreate.REGISTRATE.get().addRawLang("occult_creations.recipe.spirit_fire_blasting","Bulk Spirit Processing");
        OccultCreate.REGISTRATE.get().addRawLang("create.recipe.spirit_fire_blasting.fan","Fan behind Spirit Flame");
        OccultCreate.REGISTRATE.get().addRawLang("enum.occultism.book_of_calling.item_mode.set_crank","Set Crank");
        OccultCreate.REGISTRATE.get().addRawLang("ritual.occultcreate.summon_foliot_cranker.started","Started summoning foliot cranker.");
        OccultCreate.REGISTRATE.get().addRawLang("ritual.occultcreate.summon_foliot_cranker.conditions","Not all requirements for this ritual are met.");
        OccultCreate.REGISTRATE.get().addRawLang("ritual.occultcreate.summon_foliot_cranker.finished","Summoned foliot cranker successfully.");
        OccultCreate.REGISTRATE.get().addRawLang("ritual.occultcreate.summon_foliot_cranker.interrupted","Summoning of foliot cranker was interrupted.");
        OccultCreate.REGISTRATE.get().addRawLang("items.occultcreate.book_of_calling.message_set_crank","Set base for %s to %s");
    }
}
