package ir.sban.intelligallery.presentation.home

import androidx.compose.runtime.Composable
import ir.sban.intelligallery.R
import ir.sban.intelligallery.presentation.home.screen.AlbumsScreen
import ir.sban.intelligallery.presentation.home.screen.AllItemsScreen
import ir.sban.intelligallery.presentation.home.screen.FavoriteScreen
import ir.sban.intelligallery.presentation.home.screen.PeopleScreen
import ir.sban.intelligallery.presentation.home.screen.SearchScreen

sealed class Routes(
    val route: String,
    val name: String,
    val icon: Int,
    val screen: @Composable () -> Unit
) {
    data object People : Routes("people", "People", R.drawable.ic_people, { PeopleScreen() })
    data object Favorites :
        Routes("favorites", "Favorites", R.drawable.ic_favorite, { FavoriteScreen() })

    data object AllItems : Routes("all", "All Items", R.drawable.ic_image, { AllItemsScreen() })
    data object Albums : Routes("albums", "Albums", R.drawable.ic_albums, { AlbumsScreen() })
    data object Search : Routes("search", "Search", R.drawable.ic_search, { SearchScreen() })
}