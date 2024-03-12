package dev.katcodes.occultcreate.common.items;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.spirit.BookOfCallingItem;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.simibubi.create.AllBlocks;
import dev.katcodes.occultcreate.OccultCreate;
import dev.katcodes.occultcreate.common.crank.TurnCrankJob;
import dev.katcodes.occultcreate.common.items.itemMode.ItemModes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BookOfCallingCranker extends BookOfCallingItem {



    public BookOfCallingCranker(Properties properties) {
        super(properties, TranslationKeys.BOOK_OF_CALLING_GENERIC + "_foliot", spiritEntity -> spiritEntity.getJob().orElse(null) instanceof TurnCrankJob);
    }

    @Override
    public List<ItemMode> getItemModes() {
        return Arrays.asList(ItemModes.SET_CRANK);
    }

    @SubscribeEvent
    public static void onBlockUse(PlayerInteractEvent.RightClickBlock event) {

        if(event.getItemStack().getItem()==ModItems.BOOK_OF_CALLING_FOLIOT_CRANKER.get() && event.getLevel().getBlockState(event.getHitVec().getBlockPos()).getBlock() == AllBlocks.HAND_CRANK.get()) {
            if(event.getEntity().isCrouching()) {
                InteractionResult rest =  event.getItemStack().getItem().useOn(new UseOnContext(event.getEntity(), event.getHand(), event.getHitVec()));
                if(rest.consumesAction())
                    event.setCanceled(true);
            }
        }
    }
}
