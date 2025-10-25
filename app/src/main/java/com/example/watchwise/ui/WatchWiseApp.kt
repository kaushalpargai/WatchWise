package com.example.watchwise.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.watchwise.navigation.WatchWiseNavHost
import com.example.watchwise.util.NetworkMonitor
import com.example.watchwise.util.NetworkStatus
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun WatchWiseApp(
    networkMonitor: NetworkMonitor = koinInject()
) {
    val navController = rememberNavController()
    val networkStatus by networkMonitor.observeNetworkStatus().collectAsState(initial = NetworkStatus.Connected)
    
    var showBanner by remember { mutableStateOf(false) }
    var bannerMessage by remember { mutableStateOf("") }
    var bannerColor by remember { mutableStateOf(Color.Red) }
    
    LaunchedEffect(networkStatus) {
        when (networkStatus) {
            is NetworkStatus.Disconnected -> {
                bannerMessage = "No Network"
                bannerColor = Color(0xFFD32F2F)
                showBanner = true
            }
            is NetworkStatus.Connected -> {
                if (showBanner) {
                    bannerMessage = "Network Connected"
                    bannerColor = Color(0xFF388E3C)
                    showBanner = true
                    delay(2000)
                    showBanner = false
                } else {
                    showBanner = false
                }
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        WatchWiseNavHost(navController = navController)
        
        AnimatedVisibility(
            visible = showBanner,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                text = bannerMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bannerColor)
                    .statusBarsPadding()
                    .padding(vertical = 12.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
