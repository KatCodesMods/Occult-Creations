package dev.katcodes.occultcreate.common.ProcessingTypes;

import com.klikli_dev.occultism.crafting.recipe.ItemStackFakeInventory;
import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismParticles;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpiritProcessing implements FanProcessingType {
    private static final RecipeWrapper RECIPE_WRAPPER = new RecipeWrapper(new ItemStackHandler(1));


    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() == OccultismBlocks.SPIRIT_FIRE.get();
    }

    @Override
    public int getPriority() {
        return 150;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        ItemStackFakeInventory fakeInventory =
                new ItemStackFakeInventory(ItemStack.EMPTY);
        fakeInventory.setItem(0, stack);
        Optional<SpiritFireRecipe> recipe =
                level.getRecipeManager().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), fakeInventory, level);
        return recipe.isPresent();

    }

    @Override
    public @Nullable List<ItemStack> process(ItemStack stack, Level level) {
        ItemStackFakeInventory fakeInventory =
                new ItemStackFakeInventory(ItemStack.EMPTY);
        fakeInventory.setItem(0, stack);
        Optional<SpiritFireRecipe> recipe =
                level.getRecipeManager().getRecipeFor(OccultismRecipes.SPIRIT_FIRE_TYPE.get(), fakeInventory, level);

        if(recipe.isPresent()) {
            return RecipeApplier.applyRecipeOn(level,stack, recipe.get());
        }

        return Collections.emptyList();
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0)
            return;
        level.addParticle(OccultismParticles.SPIRIT_FIRE_FLAME.orElse(ParticleTypes.FLAME),pos.x, pos.y + .25f, pos.z, 0, 1 / 16f, 0);
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(0x8A2BE2);
        particleAccess.setAlpha(.5f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(OccultismParticles.SPIRIT_FIRE_FLAME.orElse(ParticleTypes.FLAME), .25f);
        if (random.nextFloat() < 1 / 16f)
            particleAccess.spawnExtraParticle(new BlockParticleOption(ParticleTypes.BLOCK, OccultismBlocks.SPIRIT_FIRE.get().defaultBlockState()), .25f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {

    }
}
