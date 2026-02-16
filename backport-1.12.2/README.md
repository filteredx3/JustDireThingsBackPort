# Just Dire Things – 1.12.2 Backport Workspace

This directory is an isolated Forge 1.12.2 workspace used to incrementally port the modern codebase.

> Note: `gradle-wrapper.jar` is intentionally not tracked in this repository because some PR systems reject binary files.

Target stack:
- Minecraft 1.12.2
- Forge 14.23.5.2860
- Java 8 (Temurin JRE 8u482)

## What works now
- Standalone Gradle wrapper pinned to **Gradle 4.9** for ForgeGradle 2.3.
- 1.12 lifecycle split (`preInit/init/postInit`) with dedicated `ModLifecycle` runtime bootstrap.
- 1.12 registration rewrite in progress:
  - `RegistryEvent.Register<Block>` and `RegistryEvent.Register<Item>` handlers in `ModRegistrations`
  - `@ObjectHolder` references in `ModObjectHolders`
  - client model registration driven from registered item list
- Data model migration started (modern data components -> NBT/capabilities):
  - `NbtDataHelper` + `NbtDataKeys` for item NBT state
  - `settings_tool` item with NBT-backed toggle/range/upgrades state
  - `TileEntityMachineCore` now exposes sided item+energy capabilities and persists them to NBT
  - machine internals include fuel->energy ticking, TE sync packets, and comparator output
- Networking migration started (`SimpleNetworkWrapper`):
  - `NetworkHandler` channel + packet registration in `preInit`
  - `PacketToolAction` client->server packet for `settings_tool` actions
- Client rendering/UI/event rewrites started (1.12 event bus + pipeline):
  - `MachineCoreOverlay` HUD text via `RenderGameOverlayEvent.Text`
  - `ClientKeyBindings` + `ClientInputEvents` for tool toggle key packet dispatch
  - renderer/keybind registration via `ClientProxy.preInit`
- Asset/data backporting started (1.12 recipes/loot + tag-equivalent migration):
  - crafting recipes in `assets/justdirethings/recipes`
  - `training_dummy` loot table under `assets/justdirethings/loot_tables/entities`
  - modern tag migration baseline via Forge 1.12 OreDictionary registration (`ingotFerricore`, `blockFerricore`)

- Integration + QA pass started:
  - optional-mod detection guards for JEI/The One Probe/Patchouli (`Loader.isModLoaded`)
  - QA checklist added at `docs/qa/integration-and-qa-checklist.md`
  - static QA helper script at `../scripts/backport/run_1122_qa_checks.sh`

- First real content slice:
  - `ferricore_ingot` item
  - `ferricore_block` block
  - `machine_core` block + ticking tile entity
  - `training_dummy` entity + renderer registration

## First-time build (Java 8 required)
From this directory:

```bash
export JAVA_HOME=/path/to/temurin-8
# Option A: use local Gradle 4.9 installation
gradle --version
gradle clean build

# Option B: use wrapper script after regenerating wrapper jar locally
gradle wrapper --gradle-version 4.9
./gradlew --version
./gradlew clean build
```

## QA helper
Run static QA validations from repo root:

```bash
./scripts/backport/run_1122_qa_checks.sh
```

## Release helper
Build and copy the backport jar into `release/` from repo root:

```bash
# prerequisites: Java 8 + Gradle 4.9
./scripts/backport/build_release_jar.sh
```

## Result
- Built jar appears under `build/libs/`.
- This is an **early functional backport slice**, still far from feature parity with modern Just Dire Things.

## Next migration step
- Expand recipes/loot with machine upgrades and optional mod-conditional variants (`forge:mod_loaded`) where integrations are desired.
