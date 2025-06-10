package ir.sban.intelligallery.presentation.splash

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.sban.intelligallery.presentation.arePermissionsGranted
import ir.sban.intelligallery.presentation.requestNotificationPermission
import ir.sban.intelligallery.presentation.requestPermissions
import ir.sban.intelligallery.presentation.requestStoragePermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashState: Flow<SplashState> = _splashState.asStateFlow()

    private fun getRequestablePermissions(activity: ComponentActivity): Flow<Boolean> {
        val storagePermissions = requestStoragePermission().filter {
            !activity.arePermissionsGranted(arrayOf(it))
        }
        val notificationPermissions = requestNotificationPermission().filter {
            !activity.arePermissionsGranted(arrayOf(it))
        }
        Log.d(
            "SplashActivity",
            "getRequestablePermissions: ${
                (storagePermissions + notificationPermissions).joinToString(",")
            }"
        )
        val permissions =
            arrayOf(*storagePermissions.toTypedArray(), *notificationPermissions.toTypedArray())
        return callbackFlow {
            activity.requestPermissions(permissions) { permissionsResult ->
                Log.d("SplashActivity", "PermissionsResult: $permissionsResult")
                trySend(permissionsResult)
            }

            awaitClose()
        }
    }

    fun checkStartup(activity: ComponentActivity) {
        viewModelScope.launch {
            val timerFlow = flow {
                delay(2.seconds)
                emit(true)
            }

            timerFlow.zip(getRequestablePermissions(activity)) { _, permissionsResult ->
                permissionsResult
            }.collect { permissionsResult ->
                _splashState.value = if (permissionsResult) {
                    SplashState.Success
                } else {
                    SplashState.Error
                }
            }
        }
    }
}