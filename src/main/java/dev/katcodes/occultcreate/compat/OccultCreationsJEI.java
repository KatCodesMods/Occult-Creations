package dev.katcodes.occultcreate.compat;

import static com.simibubi.create.compat.jei.CreateJEI.*;
import static dev.katcodes.occultcreate.OccultCreate.MODID;

import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;

import com.simibubi.create.compat.jei.category.FanBlastingCategory;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@JeiPlugin
public class OccultCreationsJEI implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(MODID,"jei_plugin");

    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        allCategories.clear();

//        OccultismRecipes.SPIRIT_FIRE_TYPE
        Supplier<List<SpiritFireRecipe>> recipesSupplier = () -> {
            List<SpiritFireRecipe> recipes = new ArrayList<>();
            CreateJEI.<SpiritFireRecipe>consumeTypedRecipes(recipes::add, OccultismRecipes.SPIRIT_FIRE_TYPE.get());
            return recipes;
        };

        CreateRecipeCategory<SpiritFireRecipe> category = getSpiritFireRecipeCreateRecipeCategory(recipesSupplier);
        allCategories.add(category);
        registration.addRecipeCategories(category);

    }

    private static @NotNull CreateRecipeCategory<SpiritFireRecipe> getSpiritFireRecipeCreateRecipeCategory(Supplier<List<SpiritFireRecipe>> recipesSupplier) {
        CreateRecipeCategory.Info<SpiritFireRecipe> info = new CreateRecipeCategory.Info<>(
                new mezz.jei.api.recipe.RecipeType<>(new ResourceLocation(MODID,"spirit_fire_blasting"), SpiritFireRecipe.class),
                Components.translatable("occult_creations.recipe.spirit_fire_blasting" ), new EmptyBackground(178,72), new DoubleItemIcon(() -> new ItemStack(AllItems.PROPELLER.get()), () -> new ItemStack(OccultismBlocks.SPIRIT_FIRE.get())), recipesSupplier, new ArrayList<>(){ { add(ProcessingViaFanCategory.getFan("spirit_fire_blasting"));}});

        return new FanSpiritingCategory(info);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }
}
