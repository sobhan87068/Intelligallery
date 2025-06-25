package ir.sban.intelligallery.presentation.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import ir.sban.intelligallery.R
import ir.sban.intelligallery.domain.model.MediaFile

@Composable
fun StaggeredGalleryGrid(
    modifier: Modifier = Modifier,
    items: List<MediaFile>,
    staggeringType: StaggeringType,
    spacing: Dp = 2.dp
) {
    when (items.size) {
        in 0..4 -> {
            Column(modifier = modifier) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    items.forEach { item ->
                        SimpleRowItem(item)
                    }
                }
            }
        }

        in 5..7 -> {
            Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(spacing)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    items.subList(0, 4).forEach { item ->
                        SimpleRowItem(item)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    items.subList(4, items.size).forEach { item ->
                        SimpleRowItem(item)
                    }
                }
            }
        }

        8 -> {
            FullItemGrid(modifier.aspectRatio(1f), items, staggeringType)
        }

        else -> {
            Log.e("StaggeredGalleryRow", "the number of items exceeds 8")
        }
    }
}

@Composable
private fun FullItemGrid(
    modifier: Modifier = Modifier,
    items: List<MediaFile>,
    staggeringType: StaggeringType = StaggeringType.TOP_START,
    spacing: Dp = 2.dp
) {
    var rowModifier: BoxScope.() -> Modifier = { Modifier }
    var columnModifier: BoxScope.() -> Modifier = { Modifier }
    var imageModifier: BoxScope.() -> Modifier = { Modifier }
    var imageIndex = 0
    var rowIndices = listOf<Int>()
    var columnIndices = listOf<Int>()
    when (staggeringType) {
        StaggeringType.TOP_START -> {
            rowModifier = {
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(.25f)
                    .padding(top = spacing / 2)
            }
            columnModifier = {
                Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.25f)
                    .padding(start = spacing / 2, bottom = spacing / 2)
            }
            imageModifier = {
                Modifier
                    .align(Alignment.TopStart)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.75f)
                    .padding(end = spacing / 2, bottom = spacing / 2)
            }
            imageIndex = 0
            rowIndices = listOf(4, 5, 6, 7)
            columnIndices = listOf(1, 2, 3)
        }

        StaggeringType.TOP_END -> {
            rowModifier = {
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(.25f)
                    .padding(top = spacing / 2)
            }
            columnModifier = {
                Modifier
                    .align(Alignment.TopStart)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.25f)
                    .padding(end = spacing / 2, bottom = spacing / 2)
            }
            imageModifier = {
                Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.75f)
                    .padding(start = spacing / 2, bottom = spacing / 2)
            }
            imageIndex = 1
            rowIndices = listOf(4, 5, 6, 7)
            columnIndices = listOf(0, 2, 3)
        }

        StaggeringType.BOTTOM_START -> {
            rowModifier = {
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(.25f)
                    .padding(bottom = spacing / 2)
            }
            columnModifier = {
                Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.25f)
                    .padding(start = spacing / 2, top = spacing / 2)
            }
            imageModifier = {
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.75f)
                    .padding(top = spacing / 2, end = spacing / 2)
            }
            imageIndex = 4
            rowIndices = listOf(0, 1, 2, 3)
            columnIndices = listOf(5, 6, 7)
        }

        StaggeringType.BOTTOM_END -> {
            rowModifier = {
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(.25f)
                    .padding(bottom = spacing / 2)
            }
            columnModifier = {
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.25f)
                    .padding(end = spacing / 2, top = spacing / 2)
            }
            imageModifier = {
                Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxHeight(.75f)
                    .fillMaxWidth(.75f)
                    .padding(top = spacing / 2, start = spacing / 2)
            }
            imageIndex = 7
            rowIndices = listOf(0, 1, 2, 3)
            columnIndices = listOf(4, 5, 6)
        }
    }
    Box(modifier = modifier) {
        ImageItem(modifier = imageModifier(), item = items[imageIndex])
        Row(modifier = rowModifier(), horizontalArrangement = Arrangement.spacedBy(spacing)) {
            rowIndices.forEach { idx ->
                SimpleRowItem(items[idx])
            }
        }
        Column(modifier = columnModifier(), verticalArrangement = Arrangement.spacedBy(spacing)) {
            columnIndices.forEach { idx ->
                Box(modifier = Modifier.weight(1f)) {
                    ImageItem(item = items[idx])
                }
            }
        }
    }
}

@Composable
private fun RowScope.SimpleRowItem(
    item: MediaFile
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
    ) {
        ImageItem(modifier = Modifier.fillMaxSize(), item = item)
    }
}

@Composable
private fun ImageItem(
    modifier: Modifier = Modifier,
    item: MediaFile,
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .size(500).data(item.contentUri).scale(Scale.FIT).crossfade(true)
        .allowHardware(false)
        .build()
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        if (item.isVideo) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                painter = painterResource(R.drawable.play_circle_outline),
                contentDescription = null
            )
        }
    }
}

enum class StaggeringType {
    TOP_START, TOP_END, BOTTOM_START, BOTTOM_END
}

@Preview("less than 4")
@Composable
fun LessThan4Preview() {
    StaggeredGalleryGrid(
        modifier = Modifier.fillMaxSize(),
        items = listOf(
            MediaFile(-1, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-2, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-3, "", "", -1, -1, "", 0, 0, null, 0, false),
        ), staggeringType = StaggeringType.TOP_START
    )
}

@Preview("between 4&7")
@Composable
fun Between4and7Preview() {
    StaggeredGalleryGrid(
        modifier = Modifier.fillMaxSize(),
        items = listOf(
            MediaFile(-1, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-2, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-3, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-4, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-5, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-6, "", "", -1, -1, "", 0, 0, null, 0, false),
        ), staggeringType = StaggeringType.TOP_START
    )
}

@Preview("8 items")
@Composable
fun FullRowPreview() {
    StaggeredGalleryGrid(
        modifier = Modifier.fillMaxSize(),
        items = listOf(
            MediaFile(-1, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-2, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-3, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-4, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-5, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-6, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-7, "", "", -1, -1, "", 0, 0, null, 0, false),
            MediaFile(-8, "", "", -1, -1, "", 0, 0, null, 0, false),
        ), staggeringType = StaggeringType.TOP_START
    )
}