package ir.sban.intelligallery.presentation.home.screen.all_items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sban.intelligallery.domain.model.MediaFile
import ir.sban.intelligallery.domain.usecase.GetAllMediaFilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllItemsViewModel @Inject constructor(
    getAllMediaFilesUseCase: GetAllMediaFilesUseCase
) : ViewModel() {
    private val _allItems: MutableStateFlow<List<MediaFile>> = MutableStateFlow(listOf())
    val allItems = _allItems.asStateFlow()

    init {
        viewModelScope.launch {
            getAllMediaFilesUseCase().collect {
                _allItems.emit(it)
            }
        }
    }
}