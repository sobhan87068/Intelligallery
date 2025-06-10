package ir.sban.intelligallery.presentation.splash

sealed class SplashState {
    data object Loading : SplashState()
    data object Success : SplashState()
    data object Error : SplashState()
}