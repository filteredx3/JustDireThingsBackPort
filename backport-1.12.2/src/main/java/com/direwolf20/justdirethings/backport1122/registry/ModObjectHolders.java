package com.direwolf20.justdirethings.backport1122.registry;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(JustDireThingsBackport.MODID)
public final class ModObjectHolders {
    @ObjectHolder("ferricore_ingot")
    public static final Item FERRICORE_INGOT = null;

    @ObjectHolder("ferricore_block")
    public static final Block FERRICORE_BLOCK = null;

    @ObjectHolder("machine_core")
    public static final Block MACHINE_CORE = null;

    private ModObjectHolders() {
    }
}
