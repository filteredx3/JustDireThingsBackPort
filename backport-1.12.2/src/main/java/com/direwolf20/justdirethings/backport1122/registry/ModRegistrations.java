package com.direwolf20.justdirethings.backport1122.registry;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.content.block.BlockFerricore;
import com.direwolf20.justdirethings.backport1122.content.block.BlockMachineCore;
import com.direwolf20.justdirethings.backport1122.content.item.ItemFerricoreIngot;
import com.direwolf20.justdirethings.backport1122.content.item.tool.ItemSettingsTool;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = JustDireThingsBackport.MODID)
public final class ModRegistrations {
    public static final List<Item> REGISTERED_ITEMS = new ArrayList<Item>();

    private ModRegistrations() {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(withName(new BlockFerricore(), "ferricore_block"));
        event.getRegistry().register(withName(new BlockMachineCore(), "machine_core"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Item ferricoreIngot = withName(new ItemFerricoreIngot(), "ferricore_ingot");
        event.getRegistry().register(ferricoreIngot);
        REGISTERED_ITEMS.add(ferricoreIngot);

        Item settingsTool = withName(new ItemSettingsTool(), "settings_tool");
        event.getRegistry().register(settingsTool);
        REGISTERED_ITEMS.add(settingsTool);

        registerItemBlock(event, ModObjectHolders.FERRICORE_BLOCK, "ferricore_block");
        registerItemBlock(event, ModObjectHolders.MACHINE_CORE, "machine_core");
    }

    private static void registerItemBlock(RegistryEvent.Register<Item> event, Block block, String name) {
        if (block == null) {
            throw new IllegalStateException("Expected block to be present before item registration: " + name);
        }
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(new ResourceLocation(JustDireThingsBackport.MODID, name));
        itemBlock.setUnlocalizedName(JustDireThingsBackport.MODID + "." + name);
        event.getRegistry().register(itemBlock);
        REGISTERED_ITEMS.add(itemBlock);
    }

    private static <T extends Block> T withName(T block, String name) {
        block.setRegistryName(new ResourceLocation(JustDireThingsBackport.MODID, name));
        block.setUnlocalizedName(JustDireThingsBackport.MODID + "." + name);
        return block;
    }

    private static <T extends Item> T withName(T item, String name) {
        item.setRegistryName(new ResourceLocation(JustDireThingsBackport.MODID, name));
        item.setUnlocalizedName(JustDireThingsBackport.MODID + "." + name);
        return item;
    }
}
