#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
BACKPORT_DIR="$ROOT_DIR/backport-1.12.2"
RELEASE_DIR="$ROOT_DIR/release"

mkdir -p "$RELEASE_DIR"

if ! command -v gradle >/dev/null 2>&1; then
  echo "[release] ERROR: gradle is required (Gradle 4.9 for ForgeGradle 2.3)." >&2
  exit 1
fi

GRADLE_VERSION="$(gradle --version | awk '/^Gradle / {print $2; exit}')"
if [[ "$GRADLE_VERSION" != "4.9" ]]; then
  echo "[release] ERROR: detected Gradle $GRADLE_VERSION; install/use Gradle 4.9 for backport-1.12.2." >&2
  exit 1
fi

if ! command -v java >/dev/null 2>&1; then
  echo "[release] ERROR: java is required (Java 8 for backport-1.12.2)." >&2
  exit 1
fi

JAVA_VERSION_LINE="$(java -version 2>&1 | head -n 1)"
if [[ "$JAVA_VERSION_LINE" != *"1.8."* && "$JAVA_VERSION_LINE" != *'"8'* ]]; then
  echo "[release] ERROR: detected $JAVA_VERSION_LINE; use Java 8 (Temurin 8u482 recommended)." >&2
  exit 1
fi

echo "[release] Building 1.12.2 backport jar with Gradle $GRADLE_VERSION and $JAVA_VERSION_LINE"
cd "$BACKPORT_DIR"

# Use local Gradle install because gradle-wrapper.jar is intentionally untracked.
gradle clean build

JAR_PATH="$(find "$BACKPORT_DIR/build/libs" -maxdepth 1 -type f -name '*.jar' | head -n 1)"
if [[ -z "$JAR_PATH" ]]; then
  echo "[release] ERROR: no jar produced under $BACKPORT_DIR/build/libs" >&2
  exit 1
fi

cp -f "$JAR_PATH" "$RELEASE_DIR/"
echo "[release] Copied $(basename "$JAR_PATH") to $RELEASE_DIR"
