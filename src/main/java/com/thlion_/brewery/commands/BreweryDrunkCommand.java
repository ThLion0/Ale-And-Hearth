package com.thlion_.brewery.commands;

import com.hypixel.hytale.codec.validation.Validators;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.thlion_.brewery.BreweryPlugin;
import com.thlion_.brewery.components.DrunkComponent;

import javax.annotation.Nonnull;

public class BreweryDrunkCommand extends AbstractCommandCollection {
    public BreweryDrunkCommand() {
        super("drunk", "server.commands.drunk.desc");
        this.setPermissionGroup(GameMode.Creative);
        this.addSubCommand(new DrunkSetCommand());
        this.addSubCommand(new DrunkGetCommand());
        this.addSubCommand(new DrunkResetCommand());
    }

    private boolean setDrunkLevel(
        @Nonnull CommandContext context,
        @Nonnull Store<EntityStore> store,
        @Nonnull Ref<EntityStore> ref,
        float drunkLevel
    ) {
        DrunkComponent drunkComponent = store.getComponent(ref, DrunkComponent.getComponentType());
        if (drunkComponent == null) {
            context.sendMessage(
                Message.translation("server.commands.drunk.set.nocomponent")
            );
            return false;
        }

        if (drunkLevel < 0 || drunkLevel > DrunkComponent.MAX_DRUNK_LEVEL) {
            return false;
        }

        drunkComponent.setDrunkLevel(drunkLevel);
        BreweryPlugin.get().getSoberUpSystem().updateDrunkEffects(store, ref, null, drunkComponent, false);

        return true;
    }

    private class DrunkSetCommand extends AbstractPlayerCommand {
        private final RequiredArg<Float> drunkLevel;

        public DrunkSetCommand() {
            super("set", "server.commands.drunk.set.desc");
            this.drunkLevel = this.withRequiredArg("drunkLevel", "server.commands.drunk.set.arg.level", ArgTypes.FLOAT)
                .addValidator(Validators.range(0.0F, DrunkComponent.MAX_DRUNK_LEVEL));
        }

        @Override
        protected void execute(
            @Nonnull CommandContext context,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
        ) {
            float newDrunkLevel = this.drunkLevel.get(context);
            boolean result = BreweryDrunkCommand.this.setDrunkLevel(context, store, ref, newDrunkLevel);

            if (result) {
                context.sendMessage(
                    Message.translation("server.commands.drunk.set.success")
                        .param("level", newDrunkLevel)
                );
            }
        }
    }

    private class DrunkResetCommand extends AbstractPlayerCommand {
        public DrunkResetCommand() {
            super("reset", "server.commands.drunk.reset.desc");
        }

        @Override
        protected void execute(
            @Nonnull CommandContext context,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
        ) {
            boolean result = BreweryDrunkCommand.this.setDrunkLevel(context, store, ref, 0);

            if (result) {
                context.sendMessage(
                    Message.translation("server.commands.drunk.reset.success")
                );
            }
        }
    }

    private static class DrunkGetCommand extends AbstractPlayerCommand {
        public DrunkGetCommand() {
            super("get", "server.commands.drunk.get.desc");
        }

        @Override
        protected void execute(
            @Nonnull CommandContext context,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
        ) {
            DrunkComponent drunkComponent = store.getComponent(ref, DrunkComponent.getComponentType());
            if (drunkComponent == null) {
                context.sendMessage(
                    Message.translation("server.commands.drunk.set.nocomponent")
                );
                return;
            }

            float drunkLevel = drunkComponent.getDrunkLevel();

            context.sendMessage(
                Message.translation("server.commands.drunk.get.success")
                    .param("level", drunkLevel)
                    .param("max", DrunkComponent.MAX_DRUNK_LEVEL)
            );
        }
    }
}
