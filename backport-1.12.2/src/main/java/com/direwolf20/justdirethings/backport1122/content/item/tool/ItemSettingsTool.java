package com.direwolf20.justdirethings.backport1122.content.item.tool;

import com.direwolf20.justdirethings.backport1122.JustDireThingsBackport;
import com.direwolf20.justdirethings.backport1122.data.NbtDataHelper;
import com.direwolf20.justdirethings.backport1122.data.NbtDataKeys;
import com.direwolf20.justdirethings.backport1122.network.NetworkHandler;
import com.direwolf20.justdirethings.backport1122.network.PacketToolAction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemSettingsTool extends Item {
    private static final int DEFAULT_RANGE = 3;

    public ItemSettingsTool() {
        setCreativeTab(JustDireThingsBackport.CREATIVE_TAB);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (worldIn.isRemote) {
            NetworkHandler.INSTANCE.sendToServer(new PacketToolAction(handIn, playerIn.isSneaking()));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    public static void applyServerAction(EntityPlayer playerIn, ItemStack stack, boolean sneaking) {
        boolean enabled = NbtDataHelper.getBool(stack, NbtDataKeys.TOOL_ENABLED, true);
        int range = NbtDataHelper.getInt(stack, NbtDataKeys.TOOL_RANGE, DEFAULT_RANGE);

        if (sneaking) {
            range = range >= 9 ? 1 : range + 1;
            NbtDataHelper.setInt(stack, NbtDataKeys.TOOL_RANGE, range);
            playerIn.sendStatusMessage(new TextComponentString("Range set to " + range), true);
        } else {
            enabled = !enabled;
            NbtDataHelper.setBool(stack, NbtDataKeys.TOOL_ENABLED, enabled);
            playerIn.sendStatusMessage(new TextComponentString("Tool " + (enabled ? "enabled" : "disabled")), true);
        }

        ensureDefaultUpgradeList(stack);
    }

    private static void ensureDefaultUpgradeList(ItemStack stack) {
        List<String> upgrades = NbtDataHelper.getStringList(stack, NbtDataKeys.TOOL_UPGRADES);
        if (upgrades.isEmpty()) {
            upgrades = new ArrayList<String>();
            upgrades.add("fortune");
            NbtDataHelper.setStringList(stack, NbtDataKeys.TOOL_UPGRADES, upgrades);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        boolean enabled = NbtDataHelper.getBool(stack, NbtDataKeys.TOOL_ENABLED, true);
        int range = NbtDataHelper.getInt(stack, NbtDataKeys.TOOL_RANGE, DEFAULT_RANGE);
        List<String> upgrades = NbtDataHelper.getStringList(stack, NbtDataKeys.TOOL_UPGRADES);

        tooltip.add("Enabled: " + enabled);
        tooltip.add("Range: " + range);
        tooltip.add("Upgrades: " + (upgrades.isEmpty() ? "none" : String.join(", ", upgrades)));
        tooltip.add("Right click: toggle enabled");
        tooltip.add("Sneak + right click: change range");
    }
}
