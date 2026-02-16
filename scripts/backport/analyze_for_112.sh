#!/usr/bin/env bash
set -euo pipefail

repo_root="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$repo_root"

out_file="docs/backport/compatibility-inventory.md"
date_utc="$(date -u +"%Y-%m-%d %H:%M:%SZ")"

java_file_count="$(rg --files src/main/java -g '*.java' | wc -l | tr -d ' ')"
neoforge_imports="$(rg -n '^import net\.neoforged' src/main/java -g '*.java' | wc -l | tr -d ' ')"
mc_imports="$(rg -n '^import net\.minecraft' src/main/java -g '*.java' | wc -l | tr -d ' ')"

deferred_register_hits="$(rg -n 'DeferredRegister' src/main/java -g '*.java' | wc -l | tr -d ' ')"
data_component_hits="$(rg -n 'datacomponents|DataComponent|itemStack\.set\(|itemStack\.getOrDefault\(' src/main/java -g '*.java' | wc -l | tr -d ' ')"
payload_hits="$(rg -n 'Payload|CustomPacketPayload|StreamCodec' src/main/java -g '*.java' | wc -l | tr -d ' ')"
codec_hits="$(rg -n '\bCodec\b|MapCodec|RecordCodecBuilder|streamCodec' src/main/java -g '*.java' | wc -l | tr -d ' ')"

neoforge_sample="$(rg -n '^import net\.neoforged' src/main/java -g '*.java' | head -n 12 || true)"
data_component_sample="$(rg -n 'JustDireDataComponents|itemStack\.set\(|itemStack\.getOrDefault\(' src/main/java -g '*.java' | head -n 12 || true)"
resource_sample="$(rg -n 'neoforge:|"loader"|"type"\s*:\s*"minecraft:' src/main/resources src/generated/resources -g '*.json' | head -n 12 || true)"

cat > "$out_file" <<MD
# Forge 1.12.2 Compatibility Inventory

Generated: ${date_utc}

## Repository size / surface area
- Java source files: **${java_file_count}**
- \`net.neoforged\` imports: **${neoforge_imports}**
- \`net.minecraft\` imports: **${mc_imports}**

## API-modernity indicators (need migration for 1.12.2)
- Deferred register usage: **${deferred_register_hits}** hits
- Data component-oriented item state patterns: **${data_component_hits}** hits
- Payload / modern networking patterns: **${payload_hits}** hits
- Codec-centric serialization patterns: **${codec_hits}** hits

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
\
${neoforge_sample}
\

### Data component usage sample
\
${data_component_sample}
\

### Modern recipe/loader assets sample
\
${resource_sample}
\
MD

echo "Wrote $out_file"
