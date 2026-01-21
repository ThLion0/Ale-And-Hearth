package com.thlion_.brewery.systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.thlion_.brewery.components.DrunkComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OnDeathSystem extends DeathSystems.OnDeathSystem {
    @Override
    public void onComponentAdded(@Nonnull Ref<EntityStore> ref, @Nonnull DeathComponent deathComponent, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {

    }

    @Override
    public void onComponentRemoved(@Nonnull Ref<EntityStore> ref, @Nonnull DeathComponent component, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {

    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
            DrunkComponent.getComponentType(),
            PlayerRef.getComponentType()
        );
    }
}
