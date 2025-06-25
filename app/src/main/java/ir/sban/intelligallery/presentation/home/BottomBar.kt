package ir.sban.intelligallery.presentation.home

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomBar(
    navController: NavController
) {
    // Observe the current back stack entry to update the selected item
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val selectedNavIndex = rememberSaveable { mutableIntStateOf(2) }

    val navItems = listOf(
        NavigationItem(
            name = Routes.People.name,
            icon = ImageVector.vectorResource(id = Routes.People.icon),
            route = Routes.People.route
        ),
        NavigationItem(
            name = Routes.Favorites.name,
            icon = ImageVector.vectorResource(id = Routes.Favorites.icon),
            route = Routes.Favorites.route
        ),
        NavigationItem(
            name = Routes.AllItems.name,
            icon = ImageVector.vectorResource(id = Routes.AllItems.icon),
            route = Routes.AllItems.route
        ),
        NavigationItem(
            name = Routes.Albums.name,
            icon = ImageVector.vectorResource(id = Routes.Albums.icon),
            route = Routes.Albums.route
        ),
        NavigationItem(
            name = Routes.Search.name,
            icon = ImageVector.vectorResource(id = Routes.Search.icon),
            route = Routes.Search.route
        )
    )

    // Update selectedNavIndex based on the current route
    LaunchedEffect(currentRoute) {
        val index = navItems.indexOfFirst { it.route == currentRoute }
        if (index != -1) {
            selectedNavIndex.intValue = index
        }
    }
    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavIndex.intValue == index,
                onClick = {
                    selectedNavIndex.intValue = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = item.icon,
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(text = item.name, style = MaterialTheme.typography.labelLarge)
                },
                colors = bottomBarItemStyleColors()
            )
        }
    }
}

data class NavigationItem(
    val name: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun bottomBarItemStyleColors() = NavigationBarItemDefaults.colors().copy(
    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
    selectedIndicatorColor = Color.Transparent,
)

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(rememberNavController())
}