package ro.utcluj.dsrl.microgridmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ro.utcluj.dsrl.microgridmobileapp.presentation.feat.home.HomwScreen
import ro.utcluj.dsrl.microgridmobileapp.presentation.navigation.HomeRoute
import ro.utcluj.dsrl.microgridmobileapp.presentation.ui.components.BottomNavigationBar
import ro.utcluj.dsrl.microgridmobileapp.presentation.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController)
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeRoute,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable<HomeRoute> { HomwScreen() }
                    }
                }
            }
        }
    }
}
