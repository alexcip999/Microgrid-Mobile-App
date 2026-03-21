package ro.utcluj.dsrl.microgridmobileapp.core.model

import androidx.compose.ui.graphics.Color

enum class NodeStatus(
    val primaryColor: Color,
    val secondaryColor: Color,
) {
    ONLINE(Color(0xFF34C759), Color(0x6634C759)),
    OFF(Color(0xFFE63946), Color(0x66E63946)),
    ALERT(Color(0xFFFFA500), Color(0x66FFA500)),
}