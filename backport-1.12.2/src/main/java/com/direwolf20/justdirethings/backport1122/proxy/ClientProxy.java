package com.direwolf20.justdirethings.backport1122.proxy;

import com.direwolf20.justdirethings.backport1122.client.input.ClientKeyBindings;
import com.direwolf20.justdirethings.backport1122.client.renderers.RenderTrainingDummy;
import com.direwolf20.justdirethings.backport1122.content.entity.EntityTrainingDummy;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ClientKeyBindings.register();
        RenderingRegistry.registerEntityRenderingHandler(EntityTrainingDummy.class, RenderTrainingDummy.FACTORY);
    }
}
