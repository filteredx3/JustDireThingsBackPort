package com.direwolf20.justdirethings.backport1122.client.overlay;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.content.machine.TileEntityMachineCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = JustDireThingsBackport.MODID, value = Side.CLIENT)
public final class MachineCoreOverlay {
    private MachineCoreOverlay() {
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.world == null || minecraft.player == null) {
            return;
        }

        RayTraceResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null || hitResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = hitResult.getBlockPos();
        if (pos == null) {
            return;
        }

        TileEntity tileEntity = minecraft.world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityMachineCore)) {
            return;
        }

        TileEntityMachineCore machineCore = (TileEntityMachineCore) tileEntity;
        int energy = machineCore.getEnergyStorage().getEnergyStored();
        int max = machineCore.getEnergyStorage().getMaxEnergyStored();

        ScaledResolution resolution = event.getResolution();
        int x = 6;
        int y = resolution.getScaledHeight() - 30;

        minecraft.fontRenderer.drawStringWithShadow("Machine Core", x, y, 0xFFFFFF);
        minecraft.fontRenderer.drawStringWithShadow("Energy: " + energy + " / " + max, x, y + 10, 0x55FF55);
    }
}
