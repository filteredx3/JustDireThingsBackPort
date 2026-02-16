package com.direwolf20.justdirethings.backport1122.content.block;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.content.machine.TileEntityMachineCore;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMachineCore extends Block implements ITileEntityProvider {
    public BlockMachineCore() {
        super(Material.IRON);
        setCreativeTab(JustDireThingsBackport.CREATIVE_TAB);
        setHardness(4.0F);
        setResistance(8.0F);
        setHarvestLevel("pickaxe", 1);
        setSoundType(SoundType.METAL);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMachineCore();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityMachineCore)) {
            return false;
        }

        TileEntityMachineCore machineCore = (TileEntityMachineCore) tileEntity;
        int energy = machineCore.getEnergyStorage().getEnergyStored();
        int maxEnergy = machineCore.getEnergyStorage().getMaxEnergyStored();
        int ticks = machineCore.getTicksExisted();

        playerIn.sendStatusMessage(
                new TextComponentString("Machine Core | Energy: " + energy + "/" + maxEnergy + " | Ticks: " + ticks),
                false
        );

        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMachineCore) {
            TileEntityMachineCore machineCore = (TileEntityMachineCore) tileEntity;
            ItemStack stack = machineCore.getInventory().getStackInSlot(0);
            if (!stack.isEmpty()) {
                spawnAsEntity(worldIn, pos, stack.copy());
            }
        }
        super.breakBlock(worldIn, pos, state);
    }


    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityMachineCore) {
            TileEntityMachineCore machineCore = (TileEntityMachineCore) tileEntity;
            int energy = machineCore.getEnergyStorage().getEnergyStored();
            int max = machineCore.getEnergyStorage().getMaxEnergyStored();
            if (max <= 0) {
                return 0;
            }
            return Math.min(15, (energy * 15) / max);
        }
        return 0;
    }

    @Nullable
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }
}
