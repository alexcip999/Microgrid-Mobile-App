package ro.utcluj.dsrl.microgridmobileapp.core.model

data class Neighbor(
    val id: String,
    val name: String,
    val power: String,
    val voltage: String,
    val frequency: String,
    val status: InverterStatus,
)
