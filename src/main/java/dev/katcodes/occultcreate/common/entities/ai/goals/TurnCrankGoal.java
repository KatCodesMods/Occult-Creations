package dev.katcodes.occultcreate.common.entities.ai.goals;

import com.klikli_dev.occultism.common.entity.ai.goal.PausableGoal;
import com.klikli_dev.occultism.common.entity.ai.target.BlockPosMoveTarget;
import com.klikli_dev.occultism.common.entity.ai.target.EntityMoveTarget;
import com.klikli_dev.occultism.common.entity.ai.target.IMoveTarget;
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException;
import com.klikli_dev.occultism.util.Math3DUtil;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import dev.katcodes.occultcreate.common.crank.TurnCrankJob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class TurnCrankGoal extends PausableGoal {

    protected final SpiritEntity entity;
    protected IMoveTarget moveTarget = null;


    private BlockPos getMoveTarget() {
        double angle = Math3DUtil.yaw(this.entity.position(), Math3DUtil.center(this.moveTarget.getBlockPos()));
        return this.moveTarget.getBlockPos().relative(Direction.fromYRot(angle).getOpposite());
    }



    protected Optional<BlockPos> crankPos() {
        AtomicReference<Optional<BlockPos>> res = new AtomicReference<>();
        this.entity.getJob().ifPresentOrElse(job -> {
            if (job instanceof TurnCrankJob) {
                res.set(((TurnCrankJob) job).crankPos());
            }
            else
                res.set(Optional.empty());
        }, () -> res.set(Optional.empty()));
        return res.get();
    }

    protected void setCrankPos(Optional<BlockPos> pos) {
        this.entity.getJob().ifPresent(job -> {
            if (job instanceof TurnCrankJob) {
                ((TurnCrankJob) job).setCrankPos(pos);
            }
        });
    }


    public TurnCrankGoal(SpiritEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        //do not use if there is a target to attack
        if (this.entity.getTarget() != null) {
            return false;
        }

        // if no crank set return false
        if(crankPos().isEmpty())
            return false;


        this.resetTarget();
        return !this.isPaused() && this.moveTarget != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.isPaused() && this.moveTarget != null;
    }

    public void stop() {
        this.entity.getNavigation().stop();
        this.resetTarget();
    }

    private void resetTarget() {
        //check a target block
        Optional<BlockPos> targetPos = crankPos();
        targetPos.ifPresent((pos) -> {
            this.moveTarget = new BlockPosMoveTarget(this.entity.level(), pos);
            if (!this.entity.level().getBlockState(pos).is(AllBlocks.HAND_CRANK.get())) {
                //the deposit block is not valid for depositing, so we disable this to allow exiting this task.
                this.setCrankPos(Optional.empty());
            }
        });
    }

    @Override
    public void tick() {
        if (this.moveTarget != null) {
            if (this.moveTarget.isValid()) {
                float accessDistance = 2.2f;
                double distance = this.entity.position().distanceTo(Math3DUtil.center(this.moveTarget.getBlockPos()));
                if (distance < accessDistance) {
                    //stop moving while taking out
                    this.entity.getNavigation().stop();
                } else {
                    //continue moving
                    BlockPos moveTarget = this.getMoveTarget();
                    this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(moveTarget, 0), 1.0f);
                }

                //when close enough insert item
                if (distance < accessDistance) {
                    // crank the crank
                    if(this.entity.level().getBlockEntity(moveTarget.getBlockPos()) instanceof HandCrankBlockEntity handCrankBlockEntity) {
                        this.entity.swing(InteractionHand.MAIN_HAND,true);
                        handCrankBlockEntity.turn(false);
                        this.stop();

                    }
                }
            } else {
                this.resetTarget(); //if there is no block entity, recheck
            }
        }
    }
}
