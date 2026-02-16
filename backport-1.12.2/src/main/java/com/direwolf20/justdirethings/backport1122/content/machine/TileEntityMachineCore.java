package com.direwolf20.justdirethings.backport1122.content.machine;

import com.direwolf20.justdirethings.backport1122.data.NbtDataKeys;
import com.direwolf20.justdirethings.backport1122.registry.ModObjectHolders;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityMachineCore extends TileEntity implements ITickable {
    private static final int MAX_ENERGY = 10000;
    private static final int ENERGY_PER_FUEL = 500;

    private int ticksExisted;

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    private final EnergyStorage energyStorage = new EnergyStorage(MAX_ENERGY) {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int received = super.receiveEnergy(maxReceive, simulate);
            if (received > 0 && !simulate) {
                markDirty();
            }
            return received;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int extracted = super.extractEnergy(maxExtract, simulate);
            if (extracted > 0 && !simulate) {
                markDirty();
            }
            return extracted;
        }
    };

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        ticksExisted++;

        if (ticksExisted % 20 == 0) {
            convertFuelToEnergy();
        }

        if (ticksExisted % 200 == 0) {
            markDirty();
            syncToClient();
        }
    }

    private void convertFuelToEnergy() {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.isEmpty() || stack.getItem() != ModObjectHolders.FERRICORE_INGOT) {
            return;
        }

        if (energyStorage.getEnergyStored() >= energyStorage.getMaxEnergyStored()) {
            return;
        }

        int inserted = energyStorage.receiveEnergy(ENERGY_PER_FUEL, false);
        if (inserted > 0) {
            stack.shrink(1);
            inventory.setStackInSlot(0, stack);
            markDirty();
            syncToClient();
        }
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public int getTicksExisted() {
        return ticksExisted;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NbtDataKeys.MACHINE_TICKS, ticksExisted);
        compound.setInteger(NbtDataKeys.MACHINE_ENERGY, energyStorage.getEnergyStored());
        compound.setTag("Inventory", inventory.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ticksExisted = compound.getInteger(NbtDataKeys.MACHINE_TICKS);
        inventory.deserializeNBT(compound.getCompoundTag("Inventory"));

        int stored = compound.getInteger(NbtDataKeys.MACHINE_ENERGY);
        int current = energyStorage.getEnergyStored();
        if (stored > current) {
            energyStorage.receiveEnergy(stored - current, false);
        } else if (stored < current) {
            energyStorage.extractEnergy(current - stored, false);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    private void syncToClient() {
        if (world != null) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return facing == EnumFacing.UP || facing == EnumFacing.DOWN || facing == null;
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return facing != EnumFacing.UP;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN || facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
            }
            return null;
        }
        if (capability == CapabilityEnergy.ENERGY) {
            if (facing != EnumFacing.UP) {
                return CapabilityEnergy.ENERGY.cast(energyStorage);
            }
            return null;
        }
        return super.getCapability(capability, facing);
    }
}
