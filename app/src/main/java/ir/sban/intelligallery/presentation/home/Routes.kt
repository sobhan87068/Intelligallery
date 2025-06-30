package ir.sban.intelligallery.presentation.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ir.sban.intelligallery.R
import ir.sban.intelligallery.presentation.home.screen.AlbumsScreen
import ir.sban.intelligallery.presentation.home.screen.FavoriteScreen
import ir.sban.intelligallery.presentation.home.screen.PeopleScreen
import ir.sban.intelligallery.presentation.home.screen.SearchScreen
import ir.sban.intelligallery.presentation.home.screen.all_items.AllItemsScreen

sealed class Routes @OptIn(ExperimentalSharedTransitionApi::class) constructor(
    val route: String,
    val name: String,
    val icon: Int,
    val screen: @Composable (NavController, SharedTransitionScope, AnimatedContentScope) -> Unit
) {
    @OptIn(ExperimentalSharedTransitionApi::class)
    data object People : Routes(
        "people",
        "People",
        R.drawable.ic_people,
        { navController, sharedTransitionScope, animatedContentScope ->
            PeopleScreen(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )

    @OptIn(ExperimentalSharedTransitionApi::class)
    data object Favorites : Routes(
        "favorites",
        "Favorites",
        R.drawable.ic_favorite,
        { navController, sharedTransitionScope, animatedContentScope ->
            FavoriteScreen(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )

    @OptIn(ExperimentalSharedTransitionApi::class)
    data object AllItems : Routes(
        "all",
        "All Items",
        R.drawable.ic_image,
        { navController, sharedTransitionScope, animatedContentScope ->
            AllItemsScreen(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )

    @OptIn(ExperimentalSharedTransitionApi::class)
    data object Albums : Routes(
        "albums",
        "Albums",
        R.drawable.ic_albums,
        { navController, sharedTransitionScope, animatedContentScope ->
            AlbumsScreen(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )

    @OptIn(ExperimentalSharedTransitionApi::class)
    data object Search : Routes(
        "search",
        "Search",
        R.drawable.ic_search,
        { navController, sharedTransitionScope, animatedContentScope ->
            SearchScreen(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
        }
    )
}