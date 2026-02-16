#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
BP_DIR="$ROOT_DIR/backport-1.12.2"

json_files=(
  "$BP_DIR/src/main/resources/assets/justdirethings/recipes/ferricore_block.json"
  "$BP_DIR/src/main/resources/assets/justdirethings/recipes/ferricore_ingot_from_block.json"
  "$BP_DIR/src/main/resources/assets/justdirethings/recipes/machine_core.json"
  "$BP_DIR/src/main/resources/assets/justdirethings/recipes/settings_tool.json"
  "$BP_DIR/src/main/resources/assets/justdirethings/loot_tables/entities/training_dummy.json"
)

echo "[qa] validating JSON assets"
for file in "${json_files[@]}"; do
  python -m json.tool "$file" >/dev/null
  echo "[qa] ok: $file"
done

echo "[qa] checking optional integration guards"
rg -n 'Loader\.isModLoaded\(' \
  "$BP_DIR/src/main/java/com/direwolf20/justdirethings/backport1122/integration/OptionalIntegrations.java" >/dev/null
rg -n 'registerIntegration\(logger, "(jei|theoneprobe|patchouli)"' \
  "$BP_DIR/src/main/java/com/direwolf20/justdirethings/backport1122/integration/OptionalIntegrations.java" >/dev/null

echo "[qa] checking lifecycle wiring for init/postInit QA hooks"
rg -n 'ModLifecycle\.init\(|ModLifecycle\.postInit\(' \
  "$BP_DIR/src/main/java/com/direwolf20/justdirethings/backport1122/JustDireThingsBackport.java" >/dev/null

echo "[qa] checking loot and ore dictionary registrations"
rg -n 'LootTableList\.register|OreDictionary\.registerOre' \
  "$BP_DIR/src/main/java/com/direwolf20/justdirethings/backport1122" -g '*.java' >/dev/null

echo "[qa] checking wrapper script syntax"
bash -n "$BP_DIR/gradlew"

echo "[qa] completed static QA checks successfully"
