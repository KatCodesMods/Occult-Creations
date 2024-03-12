package dev.katcodes.occultcreate.common.items.itemMode;

import com.klikli_dev.occultism.TranslationKeys;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.common.item.spirit.calling.ItemMode;
import com.klikli_dev.occultism.util.EntityUtil;
import com.klikli_dev.occultism.util.ItemNBTUtil;
import com.klikli_dev.occultism.util.TextUtil;
import com.simibubi.create.AllBlocks;
import dev.katcodes.occultcreate.OccultCreate;
import dev.katcodes.occultcreate.common.crank.TurnCrankJob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Optional;
import java.util.UUID;

public class SetCrankItemMode extends ItemMode {

    public SetCrankItemMode() {
        super("set_crank");
    }
    public boolean setSpiritCrankLocation(Player player, Level world, BlockPos pos, ItemStack stack,
                                            Direction face) {
        UUID boundSpiritId = ItemNBTUtil.getSpiritEntityUUID(stack);
        if (boundSpiritId != null) {
            Optional<SpiritEntity> boundSpirit = EntityUtil.getEntityByUuiDGlobal(world.getServer(), boundSpiritId)
                    .map(e -> (SpiritEntity) e);

            if (boundSpirit.isPresent()) {
                //update properties on entity
                OccultCreate.LOGGER.info("Setting crank location for spirit: " + boundSpirit.get().getName().getString());

                boundSpirit.get().getJob().ifPresent(job -> {
                    OccultCreate.LOGGER.info("Job is present : " + job.getClass().getName());
                    if (job instanceof TurnCrankJob turnCrankJob) {
                        OccultCreate.LOGGER.info("Job is TurnCrankJob and setting optional of pos: "+pos.toShortString());
                        turnCrankJob.setCrankPos(Optional.of(pos));
                    }
                });
                //also update control item with latest data
                ItemNBTUtil.updateItemNBTFromEntity(stack, boundSpirit.get());

                String blockName = world.getBlockState(pos).getBlock().getDescriptionId();
                player.displayClientMessage(
                        Component.translatable("items.occultcreate.book_of_calling.message_set_crank",
                                TextUtil.formatDemonName(boundSpirit.get().getName().getString()),
                                Component.translatable(blockName), face.getSerializedName()), true);
                return true;
            } else {
                player.displayClientMessage(
                        Component.translatable(
                                TranslationKeys.BOOK_OF_CALLING_GENERIC + ".message_spirit_not_found"),
                        true);
            }
        }
        return false;
    }

    @Override
    public boolean handle(BlockEntity blockEntity, Player player, Level world, BlockPos pos, ItemStack stack, Direction face) {
        if (blockEntity != null &&
                blockEntity.getBlockState().getBlock()== AllBlocks.HAND_CRANK.get()) {
            return this.setSpiritCrankLocation(player, world, pos, stack, face);
        }
        return true;
    }
}
