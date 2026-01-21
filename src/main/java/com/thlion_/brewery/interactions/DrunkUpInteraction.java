package com.thlion_.brewery.interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.thlion_.brewery.BreweryPlugin;
import com.thlion_.brewery.components.DrunkComponent;

import javax.annotation.Nonnull;

public class DrunkUpInteraction extends SimpleInstantInteraction {
    public static final BuilderCodec<DrunkUpInteraction> CODEC;

    @Override
    protected void firstRun(
        @Nonnull InteractionType interactionType,
        @Nonnull InteractionContext context,
        @Nonnull CooldownHandler cooldownHandler
    ) {
        Ref<EntityStore> ref = context.getEntity();
        Store<EntityStore> store = ref.getStore();

        ItemStack itemStack = context.getHeldItem();
        if (itemStack == null) return;

        String itemId = itemStack.getItemId();

        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        DrunkComponent drunkComponent = store.getComponent(ref, DrunkComponent.getComponentType());
        if (playerRef == null || drunkComponent == null) return;

        float drunkValue = BreweryPlugin.getConfig().getDrunkValue(itemId);

        drunkComponent.increaseDrunk(drunkValue);
        BreweryPlugin.get().getSoberUpSystem().updateDrunkEffects(store, ref, playerRef, drunkComponent, true);

        context.getState().state = InteractionState.Finished;
    }

    static {
        CODEC = BuilderCodec.builder(DrunkUpInteraction.class, DrunkUpInteraction::new, SimpleInteraction.CODEC)
            .build();
    }
}
