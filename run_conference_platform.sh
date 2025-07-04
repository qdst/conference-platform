#!/usr/bin/env bash

set -euo pipefail

# set -x

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "Running clean_build_all.sh..."
"${SCRIPT_DIR}/clean_build_all.sh"

echo "clean_build_all.sh completed."

echo "Running build_images.sh..."
"${SCRIPT_DIR}/build_images.sh"

echo "build_images.sh completed."

echo "Bringing up services via docker-compose..."
cd "${SCRIPT_DIR}"

docker-compose up -d --build --force-recreate

echo "All services are up and running."
