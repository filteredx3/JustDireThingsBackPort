package com.direwolf20.justdirethings.backport1122;

import com.direwolf20.justdirethings.backport1122.proxy.CommonProxy;
import com.direwolf20.justdirethings.backport1122.registry.ModLifecycle;
import com.direwolf20.justdirethings.backport1122.registry.ModObjectHolders;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = JustDireThingsBackport.MODID,
        name = JustDireThingsBackport.NAME,
        version = JustDireThingsBackport.VERSION,
        acceptedMinecraftVersions = "[1.12.2]"
)
public class JustDireThingsBackport {
    public static final String MODID = "justdirethings";
    public static final String NAME = "Just Dire Things (1.12.2 Backport)";
    public static final String VERSION = "1.12.2-backport-SNAPSHOT";

    @Instance(MODID)
    public static JustDireThingsBackport INSTANCE;

    @SidedProxy(
            clientSide = "com.direwolf20.justdirethings.backport1122.proxy.ClientProxy",
            serverSide = "com.direwolf20.justdirethings.backport1122.proxy.CommonProxy"
    )
    public static CommonProxy PROXY;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack createIcon() {
            if (ModObjectHolders.FERRICORE_INGOT != null) {
                return new ItemStack(ModObjectHolders.FERRICORE_INGOT);
            }
            return ItemStack.EMPTY;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModLifecycle.preInit();
        PROXY.preInit(event);
        event.getModLog().info("[{}] preInit complete", MODID);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModLifecycle.init();
        event.getModLog().info("[{}] init complete", MODID);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModLifecycle.postInit(event.getModLog());
        event.getModLog().info("[{}] postInit complete", MODID);
    }
}
