package com.nhuhuy.flamebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.nhuhuy.flamebase.auth.FlameAuth
import com.nhuhuy.flamebase.ui.navigation.FlamebaseNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    FlamebaseNavHost(
                        isLogged = FlameAuth.currentUser != null
                    )
                }
            }
        }
    }
}
