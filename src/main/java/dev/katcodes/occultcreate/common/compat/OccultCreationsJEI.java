package dev.katcodes.occultcreate.common.compat;

import com.klikli_dev.occultism.crafting.recipe.SpiritFireRecipe;
import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.foundation.utility.CreateLang;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static dev.katcodes.occultcreate.OccultCreate.MODID;

@JeiPlugin
public class OccultCreationsJEI implements IModPlugin {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MODID,"jei_plugin");
    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        Supplier<List<RecipeHolder<SpiritFireRecipe>>> recipesSupplier = () -> {
            List<RecipeHolder<SpiritFireRecipe>> recipes = new ArrayList<>();
            CreateJEI.<SpiritFireRecipe>consumeTypedRecipes(recipe -> recipes.add((RecipeHolder<SpiritFireRecipe>) recipe), OccultismRecipes.SPIRIT_FIRE_TYPE.get());
            return recipes;
        };

        CreateRecipeCategory<SpiritFireRecipe> category = getSpiritFireRecipeCreateRecipeCategory(recipesSupplier);
        allCategories.add(category);
        registration.addRecipeCategories(category);
    }

    private static @NotNull CreateRecipeCategory<SpiritFireRecipe> getSpiritFireRecipeCreateRecipeCategory(Supplier<List<RecipeHolder<SpiritFireRecipe>>> recipesSupplier) {
        CreateRecipeCategory.Info<SpiritFireRecipe> info = new CreateRecipeCategory.Info<>(
                new mezz.jei.api.recipe.RecipeType<>(ResourceLocation.fromNamespaceAndPath(MODID,"spirit_fire_blasting"), SpiritFireRecipe.class),
                CreateLang.translateDirect("occult_creations.recipe.spirit_fire_blasting" ), new EmptyBackground(178,72), new DoubleItemIcon(() -> new ItemStack(AllItems.PROPELLER.get()), () -> new ItemStack(OccultismBlocks.SPIRIT_CAMPFIRE.get())), recipesSupplier, new ArrayList<>(){ { add(ProcessingViaFanCategory.getFan("spirit_fire_blasting"));}});

        return new FanSpiritingCategory(info);
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }
}
