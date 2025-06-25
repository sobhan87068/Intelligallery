package ir.sban.intelligallery.presentation.home.screen.all_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.sban.intelligallery.presentation.home.localAllItemsViewModel
import ir.sban.intelligallery.presentation.ui.component.StaggeredGalleryGrid
import ir.sban.intelligallery.presentation.ui.component.StaggeringType

@Composable
fun AllItemsScreen(viewModel: AllItemsViewModel? = localAllItemsViewModel.current) {
    val items = viewModel?.allItems?.collectAsState()?.value
    val spacing = 2.dp

    LazyColumn(
        userScrollEnabled = true,
        verticalArrangement = Arrangement.spacedBy(spacing),
        modifier = Modifier.fillMaxSize()
    ) {
        items?.chunked(8)?.forEachIndexed { idx, list ->
            val staggeringType = when (idx % 4) {
                0 -> StaggeringType.TOP_START
                1 -> StaggeringType.TOP_END
                2 -> StaggeringType.BOTTOM_START
                3 -> StaggeringType.BOTTOM_END
                else -> StaggeringType.TOP_START
            }
            item(key = list.map { it.mediaStoreId }.joinToString("-")) {
                StaggeredGalleryGrid(
                    items = list,
                    staggeringType = staggeringType,
                    spacing = spacing
                )
            }
        }
    }
}
