package ir.sban.intelligallery.presentation.media_details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlin.math.absoluteValue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MediaDetailsScreen(
    mediaId: Long,
    onDismiss: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    val viewModel: MediaDetailsViewModel = hiltViewModel()
    val mediaFile by viewModel.getMediaFileDetails(mediaId).collectAsState(initial = null)
    var offsetY by remember { mutableFloatStateOf(0f) }
    val dismissThreshold = 300f

    // scale animates from thumbnail to full screen
    val scale by animateFloatAsState(
        targetValue = if (offsetY == 0f) 1f else 1f - (offsetY.absoluteValue / 2000f).coerceIn(
            0f,
            0.5f
        ),
        label = "scaleAnimation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (offsetY == 0f) 1f else 1f - (offsetY.absoluteValue / 1000f).coerceIn(
            0f,
            0.7f
        ),
        label = "alphaAnimation"
    )

    with(sharedTransitionScope) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = alpha))
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            if (offsetY.absoluteValue > dismissThreshold) {
                                onDismiss()
                            } else {
                                offsetY = 0f
                            }
                        },
                        onVerticalDrag = { _, dragAmount ->
                            offsetY += dragAmount
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = mediaFile?.contentUri,
                contentDescription = mediaFile?.displayName,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = offsetY
                        scaleX = scale
                        scaleY = scale
                    }
                    .sharedElement(
                        state = rememberSharedContentState(mediaId),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
        }
    }
}