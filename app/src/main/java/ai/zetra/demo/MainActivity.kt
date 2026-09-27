package ai.zetra.demo

import ai.zetra.sdk.ZetraClient
import ai.zetra.sdk.ui.ZetraChat
import ai.zetra.sdk.ui.ZetraChatActivity
import ai.zetra.sdk.ui.ZetraTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/** `10.0.2.2` is how the Android emulator reaches your computer's localhost. */
private const val LOCAL_MOCK = "http://10.0.2.2:4010/v1"
private val BRAND = Color(0xFF5B3DF5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ZetraTheme(BRAND) {
                DemoScreen(Modifier.fillMaxSize().systemBarsPadding().imePadding())
            }
        }
    }
}

@Composable
private fun DemoScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var agentId by rememberSaveable { mutableStateOf("agt_demo") }
    var useMock by rememberSaveable { mutableStateOf(true) }
    var inline by rememberSaveable { mutableStateOf(false) }
    val baseUrl = if (useMock) LOCAL_MOCK else ZetraClient.DEFAULT_BASE_URL

    if (inline) {
        // A new client per base URL; ZetraChat keeps its own ViewModel per agent.
        val client = remember(baseUrl) { ZetraClient(baseUrl = baseUrl) }
        ZetraChat(agentId = agentId.trim(), client = client, onClose = { inline = false }, modifier = modifier)
        return
    }

    Column(modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Zetra Android SDK", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Chat with any public Zetra agent. Start the mock server from zetra-api to try it offline.",
            style = MaterialTheme.typography.bodyMedium,
        )
        OutlinedTextField(
            value = agentId,
            onValueChange = { agentId = it },
            label = { Text("Agent ID") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            FilterChip(selected = useMock, onClick = { useMock = true }, label = { Text("Local mock") })
            FilterChip(selected = !useMock, onClick = { useMock = false }, label = { Text("Production") })
        }
        Text(baseUrl, style = MaterialTheme.typography.bodySmall)

        Button(
            onClick = { ZetraChatActivity.start(context, agentId.trim(), brandColor = 0xFF5B3DF5.toInt(), baseUrl = baseUrl) },
            enabled = agentId.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Open full-screen chat (Activity)") }

        OutlinedButton(
            onClick = { inline = true },
            enabled = agentId.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Embed chat in this screen (Compose)") }
    }
}
