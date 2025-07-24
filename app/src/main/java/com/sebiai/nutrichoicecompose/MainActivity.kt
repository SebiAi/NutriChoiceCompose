package com.sebiai.nutrichoicecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.sebiai.nutrichoicecompose.composables.MyTopAppBar
import com.sebiai.nutrichoicecompose.ui.theme.NutriChoiceComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainActivityContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityContent(modifier: Modifier = Modifier) {
    // Navigation
    val navController = rememberNavController()
    val startDestination = Destination.GENERAL_SEARCH

    // App bar state
    var appBarShowBackArrow by remember { mutableStateOf(false) }
    var appBarShowSettingsAction by remember { mutableStateOf(false) }
    var appBarTitle by remember { mutableStateOf("") }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        appBarShowBackArrow = controller.previousBackStackEntry != null
        appBarShowSettingsAction = destination.route == Destination.GENERAL_SEARCH.route
        appBarTitle = controller.context.getString(Destination.valueOf(destination.route!!).titleStringRes)
    }

    NutriChoiceComposeTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                    MyTopAppBar(
                        titleText = appBarTitle,
                        showBackArrow = appBarShowBackArrow,
                        onBackArrowPressed = {
                            navController.popBackStack()
                        },
                        showSettingsAction = appBarShowSettingsAction,
                        onSettingsActionClick = {
                            navController.navigate(route = Destination.SETTINGS.route)
                        }
                    )
            }
        ) { innerPadding ->
            AppNavHost(
                navController,
                startDestination,
                modifier = Modifier.padding(innerPadding).padding(12.dp)
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun MainActivityPreview() {
    MainActivityContent()
}