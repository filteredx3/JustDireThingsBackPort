# 1.12.2 Integration + QA Pass Checklist

This checklist is for validating backport stability across optional integrations, dedicated server boot, and save/load persistence.

## 1) Optional integration matrix (1.12-style)

| Integration | Mod ID | Guard mechanism | Status |
|---|---|---|---|
| JEI | `jei` | `Loader.isModLoaded("jei")` | Detection/logging wired |
| The One Probe | `theoneprobe` | `Loader.isModLoaded("theoneprobe")` | Detection/logging wired |
| Patchouli | `patchouli` | `Loader.isModLoaded("patchouli")` | Detection/logging wired |

Expected behavior:
- Missing optional mods must not crash the game or server.
- Presence/absence should be visible in post-init logs.

## 2) Dedicated server smoke tests

1. Prepare Java 8 and Gradle 4.9.
2. Build artifact: `gradle clean build` (or regenerated wrapper).
3. Start dedicated server (`gradle runServer` if task is available in your local setup).
4. Verify startup log includes:
   - mod pre/init/post lifecycle completion,
   - no missing registry errors,
   - no classloading errors from client-only classes.

## 3) Save/load and persistence checks

1. Create world and place `machine_core`.
2. Insert `ferricore_ingot` fuel; wait for energy/tick increase.
3. Quit world and reload it.
4. Validate persisted state after reload:
   - machine energy value,
   - machine inventory contents,
   - tick counter continuity.
5. Break/re-place machine and validate expected drop behavior.

## 4) Multiplayer sanity checks

1. Launch dedicated server and one client.
2. Join, use `settings_tool`, and toggle via right-click and keybind.
3. Confirm packet-driven behavior remains server-authoritative (no desync).
4. Kill `training_dummy` and verify loot table behavior.

## 5) Exit criteria for this QA lane

- Optional integrations are safely guarded with `Loader.isModLoaded`.
- Dedicated server starts without crash in a clean modpack.
- Save/load preserves machine state (inventory + energy + NBT counters).
- Core packet paths function in MP without threading exceptions.
