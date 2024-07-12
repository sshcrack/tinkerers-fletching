/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.sshcrack.tinkerers_fletching.recipe.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

import java.util.List;

/**
 * Interface that modders can implement to create new behaviors for {@link Ingredient}s.
 *
 *
 * <p>The format for custom ingredients is as follows:
 * <pre>{@code
 * {
 *    // For fabric
 *     "fabric:type": "<identifier of the serializer>",
 *     // For neoforge
 *     "type": "<identifier of the serializer>",
 *     // extra ingredient data, dependent on the serializer
 * }
 * }</pre>
 *
 * @see ArchCustomIngredientSerializer
 */
public interface ArchCustomIngredient {
    /**
     * Checks if a stack matches this ingredient.
     * The stack <strong>must not</strong> be modified in any way.
     *
     * @param stack the stack to test
     * @return {@code true} if the stack matches this ingredient, {@code false} otherwise
     */
    boolean test(ItemStack stack);

    /**
     * {@return the list of stacks that match this ingredient.}
     *
     * <p>The following guidelines should be followed for good compatibility:
     * <ul>
     *     <li>These stacks are generally used for display purposes, and need not be exhaustive or perfectly accurate.</li>
     *     <li>An exception is ingredients that {@linkplain #requiresTesting() don't require testing},
     *     for which it is important that the returned stacks correspond exactly to all the accepted {@link Item}s.</li>
     *     <li>At least one stack must be returned for the ingredient not to be considered {@linkplain Ingredient#isEmpty() empty}.</li>
     *     <li>The ingredient should try to return at least one stack with each accepted {@link Item}.
     *     This allows mods that inspect the ingredient to figure out which stacks it might accept.</li>
     * </ul>
     *
     * <p>Note: no caching needs to be done by the implementation, this is already handled by the ingredient itself.
     */
    List<ItemStack> getMatchingStacks();

    /**
     * Returns whether this ingredient always requires {@linkplain #test direct stack testing}.
     * fabric wiki: FabricIngredient#requiresTesting()
     *
     * @return {@code false} if this ingredient ignores NBT data when matching stacks, {@code true} otherwise
     */
    boolean requiresTesting();

    /**
     * {@return the serializer for this ingredient}
     *
     * <p>The serializer must have been registered using {@link ArchCustomIngredientSerializerRegistry#register(ArchCustomIngredientSerializer)}.
     */
    ArchCustomIngredientSerializer<?> getSerializer();
}
