package ir.sban.intelligallery.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.sban.intelligallery.R
import ir.sban.intelligallery.presentation.home.HomeActivity
import ir.sban.intelligallery.presentation.ui.theme.IntelligalleryTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntelligalleryTheme {
                LogoImage()
            }
        }
        handleState()
        viewModel.checkStartup(this)
    }

    private fun handleState() {
        lifecycleScope.launch {
            viewModel.splashState.collect { state ->
                when (state) {
                    SplashState.Success -> {
                        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                        finish()
                    }

                    SplashState.Error -> {
                        Log.d("SplashActivity", "handleState: Error")
                        finish()
                    }

                    SplashState.Loading -> {
                        Log.d("SplashActivity", "onCreate: Loading")
                    }
                }
            }
        }
    }
}

@Composable
fun LogoImage() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.33f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(.5f),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .alpha(.2f)
                    .fillMaxSize()
                    .scale(1.5f)
                    .offset((-100).dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LogoImage()
}