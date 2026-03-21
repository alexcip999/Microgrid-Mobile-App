package ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.utcluj.dsrl.microgridmobileapp.core.model.MicrogridNode
import ro.utcluj.dsrl.microgridmobileapp.core.model.NodeStatus
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_BG_ALPHA
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_DISTANCE_FROM_PATH
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_SIDE_HUB_EAST
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_SIDE_HUB_SOUTH
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_SIDE_NORTH_EAST
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_SIDE_NORTH_HUB
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.BADGE_SIDE_SOUTH_EAST
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.EAST_X
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.EDGE_GLOW_ALPHA
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.FLOW_ANIMATION_DURATION
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.FLOW_DASH_LENGTH
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.FLOW_GAP_LENGTH
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.NODE_GLOW_ALPHA
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.NODE_INNER_SIZE
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.NODE_OUTER_SIZE
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.NORTH_Y
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.SOUTH_Y
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.SURFACE_CARD_ALPHA
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.VIRTUAL_HEIGHT_BOUND
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.VIRTUAL_WIDTH_BOUND
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.microgrid.TopologyConstants.WEST_X
import ro.utcluj.dsrl.microgridmobileapp.presentation.ui.theme.Dimensions
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun MicrogridScreen() {
    val nodesList =
        remember {
            listOf(
                MicrogridNode("North", "2.1", "↑ 3.1", NodeStatus.ONLINE, Offset(0f, NORTH_Y)),
                MicrogridNode("East", "5.2", "↓ 2.4", NodeStatus.ONLINE, Offset(EAST_X, 0f)),
                MicrogridNode("South", "0.9", "↓ 1.8", NodeStatus.ALERT, Offset(0f, SOUTH_Y)),
                MicrogridNode("West", status = NodeStatus.OFF, relativeOffset = Offset(WEST_X, 0f)),
                MicrogridNode(
                    "HUB",
                    "3.5",
                    "↑ 5.2",
                    NodeStatus.ONLINE,
                    Offset(0f, 0f),
                    isMainHub = true,
                ),
            )
        }

    val phase by rememberInfiniteTransition(label = "flow").animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(tween(FLOW_ANIMATION_DURATION, easing = LinearEasing)),
        label = "phase",
    )

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        BackgroundGlow()

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(Dimensions.PaddingMedium),
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(Dimensions.PaddingLarge))

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = SURFACE_CARD_ALPHA),
                            shape = RoundedCornerShape(Dimensions.RadiusLarge),
                        ).border(
                            width = Dimensions.BorderThin,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(Dimensions.RadiusLarge),
                        ),
                contentAlignment = Alignment.Center,
            ) {
                BoxWithConstraints(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(Dimensions.PaddingLarge),
                    contentAlignment = Alignment.Center,
                ) {
                    val maxWidth = constraints.maxWidth.toFloat()
                    val maxHeight = constraints.maxHeight.toFloat()

                    val scaleX = (maxWidth / 2) / VIRTUAL_WIDTH_BOUND
                    val scaleY = (maxHeight / 2) / VIRTUAL_HEIGHT_BOUND
                    val globalScale = minOf(scaleX, scaleY)

                    GraphTopologyLayer(
                        nodes = nodesList,
                        phase = phase,
                        scale = globalScale,
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.PaddingMedium))
            Text(
                text = "Tap a node for detailed statistics",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
fun GraphTopologyLayer(
    nodes: List<MicrogridNode>,
    phase: Float,
    scale: Float,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TopologyPaths(nodes, phase, scale)
        EdgeDataBadges(nodes, scale)

        nodes.forEach { node ->
            val scaledOffset =
                Offset(
                    node.relativeOffset.x * scale,
                    node.relativeOffset.y * scale,
                )
            key(node.name) {
                ProfessionalNode(
                    node = node,
                    modifier =
                        Modifier.offset {
                            IntOffset(scaledOffset.x.roundToInt(), scaledOffset.y.roundToInt())
                        },
                )
            }
        }
    }
}

@Composable
fun TopologyPaths(
    nodes: List<MicrogridNode>,
    phase: Float,
    scale: Float,
) {
    val nodesMap = nodes.associateBy { it.name }
    val primary = MaterialTheme.colorScheme.primary
    val alert = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = Modifier.fillMaxSize()) {
        val dashEffect =
            PathEffect.dashPathEffect(
                floatArrayOf(
                    FLOW_DASH_LENGTH * scale,
                    FLOW_GAP_LENGTH * scale,
                ),
                phase,
            )

        fun drawProEdge(
            from: String,
            to: String,
        ) {
            val fromNode = nodesMap[from] ?: return
            val toNode = nodesMap[to] ?: return

            val start = center + (fromNode.relativeOffset * scale)
            val end = center + (toNode.relativeOffset * scale)

            val color =
                if (fromNode.status == NodeStatus.ALERT || toNode.status == NodeStatus.ALERT) {
                    alert
                } else {
                    primary
                }

            drawLine(
                color = color.copy(alpha = EDGE_GLOW_ALPHA),
                start = start,
                end = end,
                strokeWidth = 10f * scale,
                cap = StrokeCap.Round,
            )

            drawLine(
                color = color,
                start = start,
                end = end,
                strokeWidth = 2.5f,
                pathEffect = dashEffect,
                cap = StrokeCap.Round,
            )
        }

        drawProEdge("North", "HUB")
        drawProEdge("North", "East")
        drawProEdge("HUB", "East")
        drawProEdge("HUB", "South")
        drawProEdge("South", "East")
    }
}

@Composable
fun EdgeDataBadges(
    nodes: List<MicrogridNode>,
    scale: Float,
) {
    val nodesMap = nodes.associateBy { it.name }

    @Composable
    fun Badge(
        from: String,
        to: String,
        label: String,
        side: Float = 1f,
    ) {
        val start = nodesMap[from]?.relativeOffset ?: return
        val end = nodesMap[to]?.relativeOffset ?: return
        val mid = Offset((start.x + end.x) / 2, (start.y + end.y) / 2)

        val angle = atan2((end.y - start.y).toDouble(), (end.x - start.x).toDouble())
        val dist = BADGE_DISTANCE_FROM_PATH * side
        val finalX = (mid.x + cos(angle + Math.PI / 2) * dist) * scale
        val finalY = (mid.y + sin(angle + Math.PI / 2) * dist) * scale

        Box(
            modifier =
                Modifier
                    .offset { IntOffset(finalX.roundToInt(), finalY.roundToInt()) }
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = BADGE_BG_ALPHA),
                        RoundedCornerShape(Dimensions.RadiusSmall),
                    ).border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(Dimensions.RadiusSmall),
                    ).padding(Dimensions.PaddingSmall),
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Black,
            )
        }
    }

    Badge("North", "HUB", "2.3A", side = BADGE_SIDE_NORTH_HUB)
    Badge("North", "East", "0.9A", side = -BADGE_SIDE_NORTH_EAST)
    Badge("HUB", "East", "1.8A", side = BADGE_SIDE_HUB_EAST)
    Badge("HUB", "South", "1.1A", side = BADGE_SIDE_HUB_SOUTH)
    Badge("South", "East", "1.5A", side = BADGE_SIDE_SOUTH_EAST)
}

@Composable
fun ProfessionalNode(
    node: MicrogridNode,
    modifier: Modifier = Modifier,
) {
    val statusColor =
        when (node.status) {
            NodeStatus.ONLINE -> MaterialTheme.colorScheme.primary
            NodeStatus.ALERT -> MaterialTheme.colorScheme.tertiary
            NodeStatus.OFF -> MaterialTheme.colorScheme.error
        }

    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier =
                    Modifier
                        .size(NODE_OUTER_SIZE.dp)
                        .border(1.dp, statusColor.copy(alpha = NODE_GLOW_ALPHA), CircleShape),
            )
            Box(
                modifier =
                    Modifier
                        .size(NODE_INNER_SIZE.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .border(1.dp, statusColor, CircleShape)
                        .padding(Dimensions.PaddingSmall),
                contentAlignment = Alignment.Center,
            ) {
                if (node.isMainHub) {
                    Icon(
                        Icons.Default.ElectricBolt,
                        null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(Dimensions.IconMedium),
                    )
                } else {
                    Text(
                        text = node.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = statusColor,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.PaddingSmall))

        if (node.status != NodeStatus.OFF) {
            Text(
                text = "${node.powerKW}kW",
                style = MaterialTheme.typography.labelMedium,
                color = statusColor,
            )
        } else {
            Text(
                text = "OFFLINE",
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
fun BackgroundGlow() {
    val glowColor1 = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
    val glowColor2 = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val paint =
            Paint().asFrameworkPaint().apply {
                maskFilter =
                    android.graphics.BlurMaskFilter(
                        200f,
                        android.graphics.BlurMaskFilter.Blur.NORMAL,
                    )
            }
        drawContext.canvas.nativeCanvas.drawCircle(
            0f,
            size.height * 0.4f,
            450f,
            paint.apply { color = glowColor1.toArgb() },
        )
        drawContext.canvas.nativeCanvas.drawCircle(
            size.width,
            size.height * 0.7f,
            400f,
            paint.apply { color = glowColor2.toArgb() },
        )
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = "Microgrid",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Row {
            Text(
                text = "11.6 kW",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "  3/5 Online",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

object TopologyConstants {
    const val NORTH_Y = -550f
    const val SOUTH_Y = 550f
    const val EAST_X = 500f
    const val WEST_X = -500f

    const val VIRTUAL_WIDTH_BOUND = 650f
    const val VIRTUAL_HEIGHT_BOUND = 750f

    const val NODE_OUTER_SIZE = 70
    const val NODE_INNER_SIZE = 60
    const val NODE_GLOW_ALPHA = 0.2f
    const val EDGE_GLOW_ALPHA = 0.12f
    const val SURFACE_CARD_ALPHA = 0.8f
    const val BADGE_BG_ALPHA = 0.9f

    const val FLOW_ANIMATION_DURATION = 6000
    const val FLOW_DASH_LENGTH = 15f
    const val FLOW_GAP_LENGTH = 25f

    const val BADGE_DISTANCE_FROM_PATH = 50f
    const val BADGE_SIDE_NORTH_HUB = 1.2f
    const val BADGE_SIDE_NORTH_EAST = -1.2f
    const val BADGE_SIDE_HUB_EAST = 1.2f
    const val BADGE_SIDE_HUB_SOUTH = -1.2f
    const val BADGE_SIDE_SOUTH_EAST = 1.2f
}
