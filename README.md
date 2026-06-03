# Pi Telemetry — Android + IoT Device Integration

A small end-to-end project demonstrating an Android app communicating with
external hardware over a REST API.

## What it does
A Raspberry Pi 4 acts as an IoT device. It runs a Python/Flask server that
reads real hardware data (CPU temperature, load, uptime) and exposes it as a
JSON REST API. An Android app (Kotlin + Jetpack Compose) polls that endpoint
with Retrofit and displays the readings on a live dashboard that refreshes
every two seconds.

## Architecture
[ Raspberry Pi 4 ] --Flask REST API--> [ Android app / Retrofit ] --> [ live dashboard ]

## Stack
- **Device:** Raspberry Pi 4, Python, Flask
- **App:** Kotlin, Jetpack Compose, Retrofit, Gson
- **Transport:** REST over local Wi-Fi

## Running it
1. On the Pi: `pip3 install flask --break-system-packages`, then `python3 pi-server/server.py`
2. In the app, set `BASE_URL` in `ApiService.kt` to the Pi's IP (`hostname -I`).
3. Build and run on an Android device/emulator on the same network.

## How this maps to the role
This mirrors connecting an Android app to industrial hardware. Here the Pi
stands in for a device like an RFID/barcode scanner; the Flask REST layer plays
the role a manufacturer SDK would. The integration pattern — app calls an
interface, receives structured data, updates the UI — is the same. I built both
ends to understand the full path, not just the app side.

## Known limitations / next steps
- Device IP is hardcoded (would make it configurable)
- Polling instead of event-driven (would use WebSockets/MQTT for real-time)
- Network logic lives in the UI layer (would extract a ViewModel + repository)
- Cleartext HTTP for the local demo (would use HTTPS in production)