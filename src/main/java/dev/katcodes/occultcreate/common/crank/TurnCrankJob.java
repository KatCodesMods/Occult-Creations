package dev.katcodes.occultcreate.common.crank;

import com.klikli_dev.occultism.common.entity.job.SpiritJob;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import dev.katcodes.occultcreate.common.entities.ai.goals.TurnCrankGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

import java.util.Optional;

public class TurnCrankJob extends SpiritJob {
    protected TurnCrankGoal turnCrankGoal;

    @Override
    public CompoundTag serializeNBT() {
       CompoundTag tag=super.serializeNBT();
       crankPos.ifPresentOrElse(blockPos -> tag.put("crankPos", NbtUtils.writeBlockPos(blockPos)),
               () -> tag.remove("crankPos"));
       return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        if(nbt.contains("crankPos")){
            this.crankPos=Optional.of(NbtUtils.readBlockPos(nbt.getCompound("crankPos")));
        }
    }

    public Optional<BlockPos> crankPos() {
        return crankPos;
    }

    public TurnCrankJob setCrankPos(Optional<BlockPos> crankPos) {
        this.crankPos = crankPos;
        return this;
    }

    protected Optional<BlockPos> crankPos=Optional.empty();

    public TurnCrankJob(SpiritEntity entity) {
        super(entity);
    }

    @Override
    protected void onInit() {
        this.entity.targetSelector.addGoal(1, turnCrankGoal = new TurnCrankGoal(this.entity));

    }

    @Override
    public void cleanup() {
       this.entity.targetSelector.removeGoal(turnCrankGoal);
    }


}
