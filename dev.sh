#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "$0")" && pwd)"

if [ ! -d "$root/frontend/node_modules" ]; then
  npm ci --prefix "$root/frontend"
fi

(cd "$root/backend" && ./mvnw spring-boot:run) &
backend_pid=$!
(cd "$root/frontend" && npm start) &
frontend_pid=$!

cleanup() {
  kill "$backend_pid" "$frontend_pid" 2>/dev/null || true
  wait "$backend_pid" "$frontend_pid" 2>/dev/null || true
}
trap cleanup EXIT INT TERM

wait -n "$backend_pid" "$frontend_pid"
