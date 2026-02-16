package com.direwolf20.justdirethings.backport1122.data;

import com.direwolf20.justdirethings.backport1122.registry.ModObjectHolders;
import net.minecraftforge.oredict.OreDictionary;

public final class ModOreDictionary {
    private ModOreDictionary() {
    }

    public static void register() {
        if (ModObjectHolders.FERRICORE_INGOT != null) {
            OreDictionary.registerOre("ingotFerricore", ModObjectHolders.FERRICORE_INGOT);
        }

        if (ModObjectHolders.FERRICORE_BLOCK != null) {
            OreDictionary.registerOre("blockFerricore", ModObjectHolders.FERRICORE_BLOCK);
        }
    }
}
