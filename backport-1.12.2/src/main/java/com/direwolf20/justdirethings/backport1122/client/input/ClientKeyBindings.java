package com.direwolf20.justdirethings.backport1122.client.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public final class ClientKeyBindings {
    public static final String CATEGORY = "key.categories.justdirethings";

    public static final KeyBinding TOOL_TOGGLE = new KeyBinding(
            "key.justdirethings.tool_toggle",
            Keyboard.KEY_V,
            CATEGORY
    );

    private ClientKeyBindings() {
    }

    public static void register() {
        ClientRegistry.registerKeyBinding(TOOL_TOGGLE);
    }
}
