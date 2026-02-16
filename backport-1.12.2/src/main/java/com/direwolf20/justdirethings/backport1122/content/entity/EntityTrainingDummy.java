package com.direwolf20.justdirethings.backport1122.content.entity;

import com.direwolf20.justdirethings.backport1122.data.ModLootTables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTrainingDummy extends EntityMob {
    public EntityTrainingDummy(World worldIn) {
        super(worldIn);
        setSize(0.6F, 1.95F);
        experienceValue = 1;
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(9, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return ModLootTables.TRAINING_DUMMY;
    }
}
