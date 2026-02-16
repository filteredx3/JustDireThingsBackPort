package com.direwolf20.justdirethings.backport1122.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public final class NbtDataHelper {
    private NbtDataHelper() {
    }

    public static NBTTagCompound getOrCreateTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public static int getInt(ItemStack stack, String key, int defaultValue) {
        NBTTagCompound tag = getOrCreateTag(stack);
        return tag.hasKey(key, Constants.NBT.TAG_INT) ? tag.getInteger(key) : defaultValue;
    }

    public static void setInt(ItemStack stack, String key, int value) {
        getOrCreateTag(stack).setInteger(key, value);
    }

    public static boolean getBool(ItemStack stack, String key, boolean defaultValue) {
        NBTTagCompound tag = getOrCreateTag(stack);
        return tag.hasKey(key, Constants.NBT.TAG_BYTE) ? tag.getBoolean(key) : defaultValue;
    }

    public static void setBool(ItemStack stack, String key, boolean value) {
        getOrCreateTag(stack).setBoolean(key, value);
    }

    public static List<String> getStringList(ItemStack stack, String key) {
        List<String> values = new ArrayList<String>();
        NBTTagCompound tag = getOrCreateTag(stack);
        if (!tag.hasKey(key, Constants.NBT.TAG_LIST)) {
            return values;
        }

        NBTTagList list = tag.getTagList(key, Constants.NBT.TAG_STRING);
        for (int i = 0; i < list.tagCount(); i++) {
            values.add(list.getStringTagAt(i));
        }
        return values;
    }

    public static void setStringList(ItemStack stack, String key, List<String> values) {
        NBTTagList list = new NBTTagList();
        for (String value : values) {
            list.appendTag(new net.minecraft.nbt.NBTTagString(value));
        }
        getOrCreateTag(stack).setTag(key, list);
    }
}
