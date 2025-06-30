package ir.sban.intelligallery.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ir.sban.intelligallery.presentation.home.screen.all_items.AllItemsViewModel
import ir.sban.intelligallery.presentation.media_details.MediaDetailsScreen
import ir.sban.intelligallery.presentation.ui.theme.IntelligalleryTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val allItemsViewModel: AllItemsViewModel by viewModels()

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntelligalleryTheme {
                CompositionLocalProvider(localAllItemsViewModel provides allItemsViewModel) { //TODO: use hilt delegate for viewModel when net fixed
                    CompositionLocalProvider(localContentResolver provides contentResolver) {
                        val navController = rememberNavController()
                        SharedTransitionLayout {
                            val graph = navController.createGraph("home") {
                                composable("home") {
                                    HomeContent(
                                        rootController = navController,
                                        sharedTransitionScope = this@SharedTransitionLayout,
                                        animatedContentScope = this
                                    )
                                }

                                composable(
                                    route = "media-details/{mediaId}",
                                    arguments = listOf(
                                        navArgument(
                                            "mediaId",
                                            builder = { type = NavType.LongType })
                                    )
                                ) { backStackEntry ->
                                    val mediaId = backStackEntry.arguments?.getLong("mediaId")
                                    if (mediaId != null) {
                                        MediaDetailsScreen(
                                            mediaId = mediaId,
                                            onDismiss = { navController.popBackStack() },
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            animatedContentScope = this
                                        )
                                    } else {
                                        // Handle error: mediaFile is null
                                        Text("Error: Media file not found.")
                                    }
                                }
                            }

                            NavHost(
                                navController = navController,
                                graph = graph
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeContent(
    rootController: NavHostController = rememberNavController(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        val graph = navController.createGraph("all") {
            Routes::class.sealedSubclasses.forEach { routeClass ->
                val routeInstance = routeClass.objectInstance
                routeInstance?.let { route ->
                    composable(route.route) {
                        route.screen(rootController, sharedTransitionScope, animatedContentScope)
                    }
                }
            }
        }

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            graph = graph
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    IntelligalleryTheme {
        SharedTransitionLayout {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeContent(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this
                    )
                }
            }
        }
    }
}