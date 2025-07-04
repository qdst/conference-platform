#!/usr/bin/env bash
set -euo pipefail

ROOT_BUILD="./build.gradle"

mapfile -t projects < <(find . -type f -name 'build.gradle' ! -path "$ROOT_BUILD" -printf '%h\n')

if [ "${#projects[@]}" -eq 0 ]; then
  echo "No sub-projects with build.gradle found."
  exit 1
fi

for project_dir in "${projects[@]}"; do
  echo
  echo "=== Cleaning & building in ${project_dir} ==="
  (
    cd "$project_dir"

    if [ -f ./gradlew ]; then
      chmod +x ./gradlew
      ./gradlew clean build

    elif command -v gradle >/dev/null 2>&1; then
      gradle clean build

    else
      echo "Error: Neither ./gradlew nor gradle found in PATH in ${project_dir}"
      exit 1
    fi
  )
done

echo
echo "✅ All sub-projects cleaned and built."
