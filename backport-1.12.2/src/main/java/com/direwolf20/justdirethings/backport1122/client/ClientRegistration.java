package com.direwolf20.justdirethings.backport1122.client;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.registry.ModRegistrations;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = JustDireThingsBackport.MODID, value = Side.CLIENT)
public final class ClientRegistration {
    private ClientRegistration() {
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : ModRegistrations.REGISTERED_ITEMS) {
            if (item.getRegistryName() != null) {
                ModelLoader.setCustomModelResourceLocation(
                        item,
                        0,
                        new ModelResourceLocation(item.getRegistryName(), "inventory")
                );
            }
        }
    }
}
