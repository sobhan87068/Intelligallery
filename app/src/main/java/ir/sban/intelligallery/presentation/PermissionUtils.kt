package ir.sban.intelligallery.presentation

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val mediaImagePermission = android.Manifest.permission.READ_MEDIA_IMAGES

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val mediaVideoPermission = android.Manifest.permission.READ_MEDIA_VIDEO
const val storageReadPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val notificationPermission = android.Manifest.permission.POST_NOTIFICATIONS

fun requestStoragePermission(): Array<String> {
    return when (Build.VERSION.SDK_INT) {
        in Build.VERSION_CODES.TIRAMISU..Int.MAX_VALUE -> arrayOf(
            mediaImagePermission,
            mediaVideoPermission
        )

        in Build.VERSION_CODES.Q until Build.VERSION_CODES.TIRAMISU -> arrayOf(
            storageReadPermission
        )

        else -> arrayOf(storageReadPermission)
    }
}

fun requestNotificationPermission(): Array<String> {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
        return arrayOf()
    }
    return arrayOf(notificationPermission)
}

fun ComponentActivity.requestPermissions(permissions: Array<String>, onResult: (Boolean) -> Unit) {
    if (arePermissionsGranted(permissions)) {
        onResult(true)
        return
    }

    requestRuntimePermissions(registerForPermissionRequest { permissionsGranted ->
        onResult(permissionsGranted.all { it.value })
    }, permissions)
}

/**
 * Registers an activity result launcher for requesting multiple permissions and
 * provides a callback to handle the results.
 *
 * This function must be called early in the Activity's lifecycle, typically
 * during `onCreate` or as an instance variable initialized by `registerForActivityResult`.
 *
 * @param onResult Callback function that will be invoked with a map of
 *                 permission strings to boolean values indicating whether each
 *                 permission was granted.
 * @return An [ActivityResultLauncher] that you can use to launch the permission request.
 */
fun ComponentActivity.registerForPermissionRequest(
    onResult: (Map<String, Boolean>) -> Unit
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        onResult(permissions)
    }
}

/**
 * Checks if all specified permissions are granted.
 *
 * @param permissions An array of permission strings to check.
 * @return `true` if all permissions are granted, `false` otherwise.
 */
fun Activity.arePermissionsGranted(permissions: Array<String>): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}

/**
 * A convenience extension function to launch a permission request using a pre-registered launcher.
 *
 * @param launcher The [ActivityResultLauncher] obtained from [registerForPermissionRequest].
 * @param permissions The array of permission strings to request.
 */
fun Activity.requestRuntimePermissions(
    launcher: ActivityResultLauncher<Array<String>>,
    permissions: Array<String>
) {
    launcher.launch(permissions)
}