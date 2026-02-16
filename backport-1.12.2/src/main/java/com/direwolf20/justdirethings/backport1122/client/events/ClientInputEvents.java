package com.direwolf20.justdirethings.backport1122.client.events;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.client.input.ClientKeyBindings;
import com.direwolf20.justdirethings.backport1122.content.item.tool.ItemSettingsTool;
import com.direwolf20.justdirethings.backport1122.network.NetworkHandler;
import com.direwolf20.justdirethings.backport1122.network.PacketToolAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = JustDireThingsBackport.MODID, value = Side.CLIENT)
public final class ClientInputEvents {
    private ClientInputEvents() {
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (!ClientKeyBindings.TOOL_TOGGLE.isPressed()) {
            return;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP player = minecraft.player;
        if (player == null) {
            return;
        }

        EnumHand activeHand = resolveSettingsToolHand(player);
        if (activeHand == null) {
            return;
        }

        NetworkHandler.INSTANCE.sendToServer(new PacketToolAction(activeHand, player.isSneaking()));
    }

    private static EnumHand resolveSettingsToolHand(EntityPlayerSP player) {
        ItemStack heldMain = player.getHeldItem(EnumHand.MAIN_HAND);
        if (heldMain.getItem() instanceof ItemSettingsTool) {
            return EnumHand.MAIN_HAND;
        }

        ItemStack heldOff = player.getHeldItem(EnumHand.OFF_HAND);
        if (heldOff.getItem() instanceof ItemSettingsTool) {
            return EnumHand.OFF_HAND;
        }

        return null;
    }
}
