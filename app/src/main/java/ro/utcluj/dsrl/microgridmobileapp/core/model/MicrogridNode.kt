package ro.utcluj.dsrl.microgridmobileapp.core.model

import androidx.compose.ui.geometry.Offset

data class MicrogridNode(
    val name: String,
    val powerKW: String? = null,
    val flowKW: String? = null,
    val status: NodeStatus,
    val relativeOffset: Offset,
    val isMainHub: Boolean = false,
)
