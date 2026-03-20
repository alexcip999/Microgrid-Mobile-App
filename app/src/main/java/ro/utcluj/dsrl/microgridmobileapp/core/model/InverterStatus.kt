package ro.utcluj.dsrl.microgridmobileapp.core.model

enum class InverterStatus(
    val label: String,
) {
    ONLINE("Online"),
    ATTENTION("Attention"),
    OFFLINE("Offline"),
}
