#!/usr/bin/env bash
# build_images.sh — builds Docker images for each service

services=(
  back-office-gateway
  back-office-ui
  conference-control
  conference-feedback
  conference-gateway
  conference-room
  external-ui
)

for service in "${services[@]}"; do
  echo "\\nBuilding image for $service..."
  if [[ -d "$service" ]]; then
    docker build \
      -t "${service}:latest" \
      -f "$service/Dockerfile" \
      "$service"
  else
    echo "Directory '$service' not found, skipping."
  fi
done

echo "\\nAll images built successfully."
