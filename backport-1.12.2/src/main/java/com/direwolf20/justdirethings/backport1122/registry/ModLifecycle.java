package com.direwolf20.justdirethings.backport1122.registry;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.content.entity.EntityTrainingDummy;
import com.direwolf20.justdirethings.backport1122.content.machine.TileEntityMachineCore;
import com.direwolf20.justdirethings.backport1122.data.ModLootTables;
import com.direwolf20.justdirethings.backport1122.data.ModOreDictionary;
import com.direwolf20.justdirethings.backport1122.integration.OptionalIntegrations;
import com.direwolf20.justdirethings.backport1122.network.NetworkHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

public final class ModLifecycle {
    private static final int TRAINING_DUMMY_ENTITY_ID = 0;

    private ModLifecycle() {
    }

    public static void preInit() {
        NetworkHandler.registerMessages();

        GameRegistry.registerTileEntity(
                TileEntityMachineCore.class,
                new ResourceLocation(JustDireThingsBackport.MODID, "machine_core")
        );

        EntityRegistry.registerModEntity(
                new ResourceLocation(JustDireThingsBackport.MODID, "training_dummy"),
                EntityTrainingDummy.class,
                "training_dummy",
                TRAINING_DUMMY_ENTITY_ID,
                JustDireThingsBackport.INSTANCE,
                64,
                1,
                true,
                0xAAAAAA,
                0x553333
        );
    }

    public static void init() {
        ModOreDictionary.register();
        ModLootTables.register();
    }

    public static void postInit(Logger logger) {
        OptionalIntegrations.init(logger);
    }
}
