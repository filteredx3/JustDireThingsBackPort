package com.direwolf20.justdirethings.backport1122.integration;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Loader;

public final class OptionalIntegrations {
    private OptionalIntegrations() {
    }

    public static void init(Logger logger) {
        registerIntegration(logger, "jei", "JEI");
        registerIntegration(logger, "theoneprobe", "The One Probe");
        registerIntegration(logger, "patchouli", "Patchouli");
    }

    private static void registerIntegration(Logger logger, String modId, String displayName) {
        if (Loader.isModLoaded(modId)) {
            logger.info("[justdirethings] Optional integration detected: {} ({})", displayName, modId);
            return;
        }

        logger.info("[justdirethings] Optional integration not present: {} ({})", displayName, modId);
    }
}
