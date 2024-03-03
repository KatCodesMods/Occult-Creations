package dev.katcodes.occultcreate.compat;

import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import org.jetbrains.annotations.NotNull;

import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;

import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.block.Blocks;

public class FanSpiritingCategory extends ProcessingViaFanCategory<SpiritFireRecipe> {

    public FanSpiritingCategory(Info<SpiritFireRecipe> info) {
        super(info);
    }

    @Override
    protected AllGuiTextures getBlockShadow() {
        return AllGuiTextures.JEI_LIGHT;
    }

    @Override
    protected void renderAttachedBlock(@NotNull PoseStack matrixStack) {
        GuiGameElement.of(OccultismBlocks.SPIRIT_FIRE.get().defaultBlockState())
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(matrixStack);
    }


}
