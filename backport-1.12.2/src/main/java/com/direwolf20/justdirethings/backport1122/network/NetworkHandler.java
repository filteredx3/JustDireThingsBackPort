package com.direwolf20.justdirethings.backport1122.network;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(JustDireThingsBackport.MODID);

    private static int packetId;

    private NetworkHandler() {
    }

    public static void registerMessages() {
        packetId = 0;
        INSTANCE.registerMessage(PacketToolAction.Handler.class, PacketToolAction.class, packetId++, Side.SERVER);
    }
}
