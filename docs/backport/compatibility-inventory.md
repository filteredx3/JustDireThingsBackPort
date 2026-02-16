# Forge 1.12.2 Compatibility Inventory

Generated: 2026-02-16 18:12:21Z

## Repository size / surface area
- Java source files: **525**
- `net.neoforged` imports: **482**
- `net.minecraft` imports: **3478**

## API-modernity indicators (need migration for 1.12.2)
- Deferred register usage: **26** hits
- Data component-oriented item state patterns: **248** hits
- Payload / modern networking patterns: **674** hits
- Codec-centric serialization patterns: **133** hits

## High-risk migration domains
1. **Build toolchain mismatch**
   - Current project uses NeoForge ModDev + Java 21.
   - Target needs ForgeGradle for 1.12.2 + Java 8.
2. **Registry and bootstrap lifecycle**
   - Current code relies on DeferredRegister and modern event bootstrapping.
   - 1.12.2 needs classic registry events and preInit/init/postInit style flows.
3. **Data storage model**
   - Current item/block state frequently uses data components.
   - 1.12.2 requires NBT / capabilities / IEEP-style patterns.
4. **Networking layer**
   - Modern payload codecs are not available in 1.12.2.
   - Must migrate to SimpleNetworkWrapper + IMessage handlers.
5. **Data-driven assets**
   - Tags, modern loot table schema, and data generators differ from 1.12.2.
6. **Rendering stack**
   - Client rendering and model APIs differ substantially from 1.12.2.
7. **Worldgen/content definitions**
   - Modern registries, features, and JSON schemas need backport rewrites.

## Evidence samples
### NeoForge-centric imports
src/main/java/com/direwolf20/justdirethings/client/renderers/DireVertexConsumer.java:5:import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
src/main/java/com/direwolf20/justdirethings/client/renderers/DireVertexConsumerSquished.java:7:import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
src/main/java/com/direwolf20/justdirethings/client/particles/ModParticles.java:12:import net.neoforged.neoforge.registries.DeferredHolder;
src/main/java/com/direwolf20/justdirethings/client/particles/ModParticles.java:13:import net.neoforged.neoforge.registries.DeferredRegister;
src/main/java/com/direwolf20/justdirethings/client/particles/ParticleRenderDispatcher.java:10:import net.neoforged.api.distmarker.Dist;
src/main/java/com/direwolf20/justdirethings/client/particles/ParticleRenderDispatcher.java:11:import net.neoforged.bus.api.SubscribeEvent;
src/main/java/com/direwolf20/justdirethings/client/particles/ParticleRenderDispatcher.java:12:import net.neoforged.fml.common.EventBusSubscriber;
src/main/java/com/direwolf20/justdirethings/client/particles/ParticleRenderDispatcher.java:13:import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
src/main/java/com/direwolf20/justdirethings/client/particles/paradoxparticle/ParadoxParticle.java:16:import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
src/main/java/com/direwolf20/justdirethings/client/particles/paradoxparticle/ParadoxParticle.java:17:import net.neoforged.neoforge.fluids.FluidStack;
src/main/java/com/direwolf20/justdirethings/client/KeyBindings.java:11:import net.neoforged.api.distmarker.Dist;
src/main/java/com/direwolf20/justdirethings/client/KeyBindings.java:12:import net.neoforged.bus.api.SubscribeEvent;

### Data component usage sample
src/main/java/com/direwolf20/justdirethings/client/screens/PocketGeneratorScreen.java:8:import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
src/main/java/com/direwolf20/justdirethings/client/screens/PocketGeneratorScreen.java:50:            int counter = pocketGenerator.getOrDefault(JustDireDataComponents.POCKETGEN_COUNTER, 0);
src/main/java/com/direwolf20/justdirethings/client/screens/PocketGeneratorScreen.java:129:        int maxBurn = pocketGenerator.getOrDefault(JustDireDataComponents.POCKETGEN_MAXBURN, 0);
src/main/java/com/direwolf20/justdirethings/client/screens/PocketGeneratorScreen.java:130:        int counter = pocketGenerator.getOrDefault(JustDireDataComponents.POCKETGEN_COUNTER, 0);
src/main/java/com/direwolf20/justdirethings/client/overlays/AbilityCooldownOverlay.java:3:import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
src/main/java/com/direwolf20/justdirethings/client/overlays/AbilityCooldownOverlay.java:42:            if (!itemStack.has(JustDireDataComponents.ABILITY_COOLDOWNS)) continue;
src/main/java/com/direwolf20/justdirethings/client/overlays/AbilityCooldownOverlay.java:43:            List<ToolRecords.AbilityCooldown> abilityCooldowns = itemStack.get(JustDireDataComponents.ABILITY_COOLDOWNS);
src/main/java/com/direwolf20/justdirethings/util/TooltipHelpers.java:4:import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
src/main/java/com/direwolf20/justdirethings/util/TooltipHelpers.java:106:        ComponentItemHandler handler = new ComponentItemHandler(stack, JustDireDataComponents.ITEMSTACK_HANDLER.get(), 1);
src/main/java/com/direwolf20/justdirethings/util/TooltipHelpers.java:109:            tooltip.add(Component.translatable("justdirethings.pocketgeneratorburntime", stack.getOrDefault(JustDireDataComponents.POCKETGEN_COUNTER, 0), stack.getOrDefault(JustDireDataComponents.POCKETGEN_MAXBURN, 0)).withStyle(ChatFormatting.DARK_RED));
src/main/java/com/direwolf20/justdirethings/JustDireThings.java:13:import com.direwolf20.justdirethings.common.items.datacomponents.JustDireDataComponents;
src/main/java/com/direwolf20/justdirethings/JustDireThings.java:77:        event.registerItem(Capabilities.ItemHandler.ITEM, (itemStack, context) -> new ComponentItemHandler(itemStack, JustDireDataComponents.ITEMSTACK_HANDLER.get(), 1),

### Modern recipe/loader assets sample
src/main/resources/data/justdirethings/recipe/my_book_recipe_shapeless.json:2:  "neoforge:conditions": [
src/main/resources/data/justdirethings/recipe/my_book_recipe_shapeless.json:4:      "type": "neoforge:mod_loaded",
src/main/resources/data/justdirethings/recipe/my_book_recipe_shapeless.json:8:  "type": "minecraft:crafting_shapeless",
src/generated/resources/data/justdirethings/loot_table/blocks/gooblock_tier2.json:2:  "type": "minecraft:block",
src/generated/resources/data/justdirethings/loot_table/blocks/gooblock_tier2.json:13:          "type": "minecraft:item",
src/generated/resources/data/justdirethings/loot_table/blocks/generatorfluidt1.json:2:  "type": "minecraft:block",
src/generated/resources/data/justdirethings/loot_table/blocks/generatorfluidt1.json:13:          "type": "minecraft:item",
src/generated/resources/data/justdirethings/loot_table/blocks/raw_coal_t1_ore.json:2:  "type": "minecraft:block",
src/generated/resources/data/justdirethings/loot_table/blocks/raw_coal_t1_ore.json:8:          "type": "minecraft:alternatives",
src/generated/resources/data/justdirethings/loot_table/blocks/raw_coal_t1_ore.json:11:              "type": "minecraft:item",
src/generated/resources/data/justdirethings/loot_table/blocks/raw_coal_t1_ore.json:32:              "type": "minecraft:item",
src/generated/resources/data/justdirethings/loot_table/blocks/raw_coal_t1_ore.json:37:                    "type": "minecraft:uniform",
