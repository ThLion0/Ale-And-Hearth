package com.thlion_.brewery.events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.thlion_.brewery.components.DrunkComponent;

import javax.annotation.Nonnull;

public class BreweryPlayerReadyEvent {
    public static void handle(@Nonnull PlayerReadyEvent event) {
        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();
        World world = store.getExternalData().getWorld();

        world.execute(() -> store.ensureComponent(ref, DrunkComponent.getComponentType()));
    }
}
