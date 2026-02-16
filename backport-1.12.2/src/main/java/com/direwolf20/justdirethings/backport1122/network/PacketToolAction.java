package com.direwolf20.justdirethings.backport1122.network;

import com.direwolf20.justdirethings.backport1122.content.item.tool.ItemSettingsTool;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketToolAction implements IMessage {
    private int hand;
    private boolean sneaking;

    public PacketToolAction() {
    }

    public PacketToolAction(EnumHand hand, boolean sneaking) {
        this.hand = hand == EnumHand.MAIN_HAND ? 0 : 1;
        this.sneaking = sneaking;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hand = buf.readInt();
        sneaking = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(hand);
        buf.writeBoolean(sneaking);
    }

    public static class Handler implements IMessageHandler<PacketToolAction, IMessage> {
        @Override
        public IMessage onMessage(PacketToolAction message, MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    EnumHand targetHand = message.hand == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                    ItemStack stack = player.getHeldItem(targetHand);
                    if (stack.getItem() instanceof ItemSettingsTool) {
                        ItemSettingsTool.applyServerAction(player, stack, message.sneaking);
                    }
                }
            });
            return null;
        }
    }
}
