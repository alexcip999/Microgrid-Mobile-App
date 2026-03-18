package ro.utcluj.dsrl.microgridmobileapp.presentation.feat.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ro.utcluj.dsrl.microgridmobileapp.presentation.ui.theme.Dimensions

@Composable
fun HomwScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(Dimensions.PaddingMedium),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier
                        .size(Dimensions.IconLarge)
                        .background(MaterialTheme.colorScheme.primary.copy(0.2f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Default.Bolt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(Modifier.width(Dimensions.PaddingLarge / 2))
            Column {
                Text(
                    "MicroGrid Monitor",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    "Local Network • 5 nodes",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(Modifier.height(Dimensions.PaddingLarge))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    "Main Inverter",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    "ID: inv-001",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(0.1f),
                shape = RoundedCornerShape(Dimensions.RadiusMedium),
            ) {
                Row(
                    Modifier.padding(
                        horizontal = Dimensions.PaddingMedium,
                        vertical = Dimensions.PaddingSmall,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        Modifier
                            .size(Dimensions.RadiusSmall)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                    )
                    Spacer(Modifier.width(Dimensions.PaddingSmall))
                    Text(
                        "Online",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }

        Spacer(Modifier.height(Dimensions.PaddingMedium))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(Dimensions.RadiusLarge),
        ) {
            Column(
                Modifier
                    .padding(Dimensions.PaddingLarge)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "ACTIVE POWER",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "3,450",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displayLarge,
                    )
                    Spacer(Modifier.width(Dimensions.PaddingSmall))
                    Text(
                        "W",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(Dimensions.IconSmall),
                    )
                    Spacer(Modifier.width(Dimensions.PaddingSmall))
                    Text(
                        "18.7 kWh today",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

        Spacer(Modifier.height(Dimensions.PaddingSmall))

        Row(Modifier.fillMaxWidth()) {
            MetricCard(
                "Voltage",
                "230.5",
                "V",
                Icons.Default.Speed,
                MaterialTheme.colorScheme.secondary,
                Modifier.weight(1f),
            )
            MetricCard(
                "Current",
                "15.0",
                "A",
                Icons.Default.Timeline,
                MaterialTheme.colorScheme.secondary,
                Modifier.weight(1f),
            )
        }
        Row(Modifier.fillMaxWidth()) {
            MetricCard(
                "Frequency",
                "50.01",
                "Hz",
                Icons.Default.Bolt,
                MaterialTheme.colorScheme.primary,
                Modifier.weight(1f),
            )
            MetricCard(
                "Temperature",
                "42",
                "°C",
                Icons.Default.DeviceThermostat,
                MaterialTheme.colorScheme.tertiary,
                Modifier.weight(1f),
            )
        }

        MetricCard(
            title = "Battery SOC",
            value = "78",
            unit = "%",
            icon = Icons.Default.BatteryChargingFull,
            iconColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(Dimensions.PaddingSmall / 2),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(Dimensions.RadiusMedium),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.PaddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimensions.PaddingSmall),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(Dimensions.IconSmall),
                )
                Spacer(Modifier.width(Dimensions.PaddingSmall))
                Text(
                    title.uppercase(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    value,
                    color = iconColor,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.width(Dimensions.PaddingSmall / 2))
                Text(
                    unit,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
