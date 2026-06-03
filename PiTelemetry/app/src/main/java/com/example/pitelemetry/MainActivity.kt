package com.example.pitelemetry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { Dashboard() }
        }
    }
}

@Composable
fun Dashboard() {
    var data by remember { mutableStateOf<Telemetry?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            try {
                data = Api.service.getTelemetry()
                error = null
            } catch (e: Exception) {
                error = e.message
            }
            delay(2000)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pi Device Telemetry", fontSize = 24.sp)
        Spacer(Modifier.height(24.dp))
        when {
            error != null -> Text("Error: $error")
            data == null -> CircularProgressIndicator()
            else -> {
                val d = data!!
                Stat("Device", d.device)
                Stat("CPU Temp", "${d.temp_c} °C")
                Stat("CPU Load", "${d.load}")
                Stat("Uptime", "${d.uptime_s}s")
            }
        }
    }
}

@Composable
fun Stat(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontSize = 16.sp)
            Text(value, fontSize = 16.sp)
        }
    }
}