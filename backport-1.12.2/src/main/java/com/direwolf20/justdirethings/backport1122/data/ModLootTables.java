package com.direwolf20.justdirethings.backport1122.data;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public final class ModLootTables {
    public static final ResourceLocation TRAINING_DUMMY =
            new ResourceLocation(JustDireThingsBackport.MODID, "entities/training_dummy");

    private ModLootTables() {
    }

    public static void register() {
        LootTableList.register(TRAINING_DUMMY);
    }
}
