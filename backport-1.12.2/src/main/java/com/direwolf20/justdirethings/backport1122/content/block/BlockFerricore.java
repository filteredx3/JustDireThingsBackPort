package com.direwolf20.justdirethings.backport1122.content.block;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockFerricore extends Block {
    public BlockFerricore() {
        super(Material.IRON);
        setCreativeTab(JustDireThingsBackport.CREATIVE_TAB);
        setHardness(5.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 2);
        setSoundType(SoundType.METAL);
    }
}
