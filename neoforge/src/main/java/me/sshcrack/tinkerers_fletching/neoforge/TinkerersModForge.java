package me.sshcrack.tinkerers_fletching.neoforge;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.TinkerersModClient;
import me.sshcrack.tinkerers_fletching.client.registries.neoforge.ScreenRegisterImpl;
import me.sshcrack.tinkerers_fletching.server.TinkerersModDedicatedServer;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@SuppressWarnings("unused")
@Mod(TinkerersMod.MOD_ID)
public final class TinkerersModForge {
    public static DeferredRegister<IngredientType<?>> INGREDIENT_TYPE = DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, TinkerersMod.MOD_ID);
    public static DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, TinkerersMod.MOD_ID);


    public TinkerersModForge(IEventBus eventBus) {
        eventBus.addListener(this::onClientSetup);
        eventBus.addListener(this::onCommonSetup);

        eventBus.addListener(this::onRegisterScreens);

        // Run our common setup.
        INGREDIENT_TYPE.register(eventBus);
        ENTITY_TYPE.register(eventBus);

        TinkerersMod.init();

    }

    public void onClientSetup(FMLClientSetupEvent event) {
        TinkerersModClient.init();
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        TinkerersMod.setup();
    }

    public void onRegisterScreens(RegisterMenuScreensEvent event) {
        TinkerersModClient.registerScreens();
        TinkerersMod.LOGGER.info("Registering a total of {} screens", ScreenRegisterImpl.screenFactories.size());

        // All of this junk just to register without actually caring about generics as it should always match
        for (var screenFactory : ScreenRegisterImpl.screenFactories) {
            screenFactory.accept(event);
        }
    }

    public void onDedicatedSetup(FMLDedicatedServerSetupEvent event) {
        TinkerersModDedicatedServer.init();
    }
}
