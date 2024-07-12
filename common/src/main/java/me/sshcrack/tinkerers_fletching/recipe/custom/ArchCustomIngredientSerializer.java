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

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

/**
 * Serializer for a {@link ArchCustomIngredient}.
 *
 * @param <T> the type of the custom ingredient
 */
public interface ArchCustomIngredientSerializer<T extends ArchCustomIngredient> {
    /**
     * {@return the identifier of this serializer}.
     */
    Identifier getIdentifier();

    /**
     * {@return the codec}.
     *
     * <p>Codecs are used to read the ingredient from the recipe JSON files.
     *
     * @see Ingredient#ALLOW_EMPTY_CODEC
     * @see Ingredient#DISALLOW_EMPTY_CODEC
     */
    MapCodec<T> getCodec(boolean allowEmpty);

    /**
     * {@return the packet codec for serializing this ingredient}.
     *
     * @see Ingredient#PACKET_CODEC
     */
    PacketCodec<RegistryByteBuf, T> getPacketCodec();
}
