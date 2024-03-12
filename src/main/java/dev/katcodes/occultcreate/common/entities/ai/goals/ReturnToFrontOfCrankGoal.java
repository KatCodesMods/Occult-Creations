package dev.katcodes.occultcreate.common.entities.ai.goals;

import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import dev.katcodes.occultcreate.common.crank.TurnCrankJob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ReturnToFrontOfCrankGoal extends Goal {

    protected final SpiritEntity entity;
    protected int executionChance;

    public ReturnToFrontOfCrankGoal(SpiritEntity entity) {
        this(entity, 10);
    }

    public ReturnToFrontOfCrankGoal(SpiritEntity entity, int executionChance) {
        this.entity = entity;
        this.executionChance = executionChance;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }


    @Override
    public boolean canUse() {
        //fire on a slow tick based on chance
        long worldTime = this.entity.level().getGameTime() % 10;
        if (this.entity.getNoActionTime() >= 100 && worldTime != 0) {
            return false;
        }
        if (this.entity.getRandom().nextInt(this.executionChance) != 0 && worldTime != 0) {
            return false;
        }

        return hasCrankPos();
    }
    protected boolean hasCrankPos() {
        return this.entity.getJob().isPresent()
                && this.entity.getJob().get() instanceof TurnCrankJob
                && ((TurnCrankJob)this.entity.getJob().get()).crankPos().isPresent();
    }

    @Override
    public void tick() {
        if (!hasCrankPos()) {
            this.stop();
            this.entity.getNavigation().stop();
        } else {
            this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(
                    ((TurnCrankJob)this.entity.getJob().get()).crankPos().orElse(this.entity.blockPosition()), 0), 1.0f);
            double distance = this.entity.position().distanceTo(
                    Vec3.atCenterOf(((TurnCrankJob)this.entity.getJob().get()).crankPos().orElse(this.entity.blockPosition())));
            if (distance < 1F) {
                this.entity.setDeltaMovement(0, 0, 0);
                this.entity.getNavigation().stop();
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.entity.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.entity.getNavigation().moveTo(this.entity.getNavigation().createPath(
                ((TurnCrankJob)this.entity.getJob().get()).crankPos().orElse(this.entity.blockPosition()), 0), 1.0f);
        super.start();
    }
}
