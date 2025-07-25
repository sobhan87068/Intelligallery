package ir.sban.intelligallery.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import dagger.hilt.android.AndroidEntryPoint
import ir.sban.intelligallery.presentation.home.screen.all_items.AllItemsViewModel
import ir.sban.intelligallery.presentation.ui.theme.IntelligalleryTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val allItemsViewModel: AllItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntelligalleryTheme {
                CompositionLocalProvider(localAllItemsViewModel provides allItemsViewModel) { //TODO: use hilt delegate for viewModel when net fixed
                    CompositionLocalProvider(localContentResolver provides contentResolver) {
                        HomeContent()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeContent() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        val graph = navController.createGraph("all") {
            Routes::class.sealedSubclasses.forEach { routeClass ->
                val routeInstance = routeClass.objectInstance
                routeInstance?.let { route -> composable(route.route) { route.screen() } }
            }
        }

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            graph = graph
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntelligalleryTheme {
        HomeContent()
    }
}