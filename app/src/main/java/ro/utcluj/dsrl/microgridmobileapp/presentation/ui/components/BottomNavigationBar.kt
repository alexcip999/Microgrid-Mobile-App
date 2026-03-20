package ro.utcluj.dsrl.microgridmobileapp.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ro.utcluj.dsrl.microgridmobileapp.presentation.navigation.HomeRoute
import ro.utcluj.dsrl.microgridmobileapp.presentation.navigation.MicrogridRoute
import ro.utcluj.dsrl.microgridmobileapp.presentation.navigation.NeighborsRoute
import ro.utcluj.dsrl.microgridmobileapp.presentation.navigation.SettingsRoute
import ro.utcluj.dsrl.microgridmobileapp.presentation.ui.theme.Dimensions

val items =
    listOf(
        Triple("My Inverter", Icons.Default.Home, HomeRoute),
        Triple("Neighbors", Icons.Default.Group, NeighborsRoute),
        Triple("Microgrid", Icons.Default.AccountTree, MicrogridRoute),
        Triple("Settings", Icons.Default.Settings, SettingsRoute),
    )

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = Dimensions.ElevationSmall,
    ) {
        items.forEach { (label, icon, route) ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(text = label, style = MaterialTheme.typography.labelMedium) },
                colors =
                    NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
            )
        }
    }
}
