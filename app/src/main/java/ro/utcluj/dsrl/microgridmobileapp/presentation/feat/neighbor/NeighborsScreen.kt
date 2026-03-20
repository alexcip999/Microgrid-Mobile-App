package ro.utcluj.dsrl.microgridmobileapp.presentation.feat.neighbor

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.utcluj.dsrl.microgridmobileapp.core.model.InverterStatus
import ro.utcluj.dsrl.microgridmobileapp.core.model.Neighbor
import ro.utcluj.dsrl.microgridmobileapp.presentation.ui.theme.Dimensions

private val AvailableTagSize = 6.dp

@Composable
fun NeighborsScreen() {
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
            Spacer(Modifier.width(Dimensions.PaddingMedium))
            Column {
                Text("MicroGrid Monitor", style = MaterialTheme.typography.headlineMedium)
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
            Text("Neighbor Inverters", style = MaterialTheme.typography.headlineMedium)
            Text(
                text =
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(
                                "2/4 ",
                            )
                        }
                        append("online")
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(Modifier.height(Dimensions.PaddingMedium))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(Dimensions.PaddingMedium)) {
            items(neighbors) { neighbor ->
                NeighborItemCard(neighbor)
            }
        }
    }
}

@Composable
fun NeighborItemCard(neighbor: Neighbor) {
    val statusColor =
        when (neighbor.status) {
            InverterStatus.ONLINE -> MaterialTheme.colorScheme.primary
            InverterStatus.ATTENTION -> MaterialTheme.colorScheme.tertiary
            InverterStatus.OFFLINE -> MaterialTheme.colorScheme.error
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(Dimensions.RadiusMedium),
    ) {
        Row(
            modifier = Modifier.padding(Dimensions.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = neighbor.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    Spacer(Modifier.width(Dimensions.PaddingSmall))

                    Surface(
                        color = statusColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(Dimensions.RadiusMedium),
                    ) {
                        Row(
                            Modifier.padding(
                                horizontal = Dimensions.PaddingSmall,
                                vertical = Dimensions.PaddingSmall / 2,
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                Modifier.size(AvailableTagSize).background(statusColor, CircleShape),
                            )
                            Spacer(Modifier.width(Dimensions.PaddingSmall))
                            Text(
                                neighbor.status.label,
                                color = statusColor,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(Dimensions.PaddingSmall))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        neighbor.power,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        " W",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )

                    Spacer(Modifier.width(Dimensions.PaddingMedium))
                    Text(
                        neighbor.voltage,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        " V",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )

                    Spacer(Modifier.width(Dimensions.PaddingMedium))
                    Text(
                        neighbor.frequency,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        " Hz",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            )
        }
    }
}

val neighbors =
    listOf(
        Neighbor(
            "1",
            "North Neighbor - Ionescu House",
            "2,100",
            "229.8",
            "50.00",
            InverterStatus.ONLINE,
        ),
        Neighbor(
            "2",
            "East Neighbor - Block A3",
            "5,200",
            "231.2",
            "49.99",
            InverterStatus.ONLINE,
        ),
        Neighbor(
            "3",
            "South Neighbor - Popa Workshop",
            "890",
            "225.1",
            "49.95",
            InverterStatus.ATTENTION,
        ),
        Neighbor("4", "West Neighbor - Solar Farm", "0", "0.0", "0.00", InverterStatus.OFFLINE),
    )
