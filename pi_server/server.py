from flask import Flask, jsonify
import subprocess, time

app = Flask(__name__)
START = time.time()

def cpu_temp():
    # Hardware reading from Pi's sensor
    with open("/sys/class/thermal/thermal_zone0/temp") as f:
        return round(int(f.read()) / 1000.0, 1)

def cpu_load():
    # Average load over 60 second interval
    return round(__import__("os").getloadavg()[0], 2)

@app.route("/api/telemetry")
def telemetry():
    return jsonify({
        "temp_c": cpu_temp(),
        "load": cpu_load(),
        "uptime_s": int(time.time() - START),
        "device": "raspberry-pi-4"
    })

@app.route("/api/health")
def health():
    return jsonify({"status": "ok"})

if __name__ == "__main__":
    # This will make it reachable from phone
    app.run(host="0.0.0.0", port=5000)
