package ir.sban.intelligallery.presentation.media_details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sban.intelligallery.domain.usecase.GetMediaFileDetailsUseCase
import javax.inject.Inject

@HiltViewModel
class MediaDetailsViewModel @Inject constructor(
    private val getMediaFileDetailsUseCase: GetMediaFileDetailsUseCase
) : ViewModel() {
    fun getMediaFileDetails(id: Long) = getMediaFileDetailsUseCase(id)
}