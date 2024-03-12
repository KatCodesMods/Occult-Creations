package dev.katcodes.occultcreate.common.items.itemMode;

import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;

import static com.klikli_dev.occultism.common.item.spirit.calling.ItemModes.addItemMode;

public class ItemModes {
    public static ItemMode SET_CRANK;

    public static void init() {
        addItemMode(SET_CRANK = new SetCrankItemMode());
    }

}
