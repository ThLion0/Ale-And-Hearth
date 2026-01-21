package com.thlion_.brewery.systems;

import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.thlion_.brewery.utils.Utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlaceBlockSystem extends EntityEventSystem<EntityStore, PlaceBlockEvent> {
    public PlaceBlockSystem() {
        super(PlaceBlockEvent.class);
    }

    @Override
    public void handle(
        int index,
        @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
        @Nonnull Store<EntityStore> store,
        @Nonnull CommandBuffer<EntityStore> commandBuffer,
        @Nonnull PlaceBlockEvent event
    ) {
        if (event.isCancelled()) return;

        ItemStack itemStack = event.getItemInHand();
        if (itemStack == null) return;

        boolean hasTag = Utils.isItemStackHasTag(itemStack.getItem(), "Type", "Brewery_Drink");

        if (itemStack.getDurability() != itemStack.getMaxDurability() && hasTag) {
            event.setCancelled(true);
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
