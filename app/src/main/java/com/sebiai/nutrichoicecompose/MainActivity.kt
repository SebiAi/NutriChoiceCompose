package com.sebiai.nutrichoicecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.sebiai.nutrichoicecompose.composables.MyTopAppBar
import com.sebiai.nutrichoicecompose.navigation.AppNavHost
import com.sebiai.nutrichoicecompose.navigation.getTitleForCurrentRoute
import com.sebiai.nutrichoicecompose.navigation.routes.HomeNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.OnboardingNavRoute
import com.sebiai.nutrichoicecompose.navigation.routes.navigateToSettingsScreen
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
fun MainActivityContent(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val appState by appViewModel.appState.collectAsStateWithLifecycle()

    // Navigation
    val navController = rememberNavController()

    // App bar state
    var appBarShowBackArrow by remember { mutableStateOf(false) }
    var appBarShowSettingsAction by remember { mutableStateOf(false) }
    var appBarTitle by remember { mutableStateOf("") }

    // Determine start destination based on onboarding state
    val startDestination = if (appState.isOnboardingComplete) HomeNavRoute else OnboardingNavRoute

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        appBarShowBackArrow = controller.previousBackStackEntry != null

        destination.route?.let { route ->
            // routes have the qualifiedName of the class plus a url like arguments
            // when a data class is used
            val routeQualifiedName = route.substringBefore('/')
            appBarTitle = getTitleForCurrentRoute(controller.context, route)

            appBarShowSettingsAction = routeQualifiedName == HomeNavRoute::class.qualifiedName!!
        }
    }

    NutriChoiceComposeTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                // Only show app bar after onboarding
                if (appState.isOnboardingComplete) {
                    MyTopAppBar(
                        titleText = appBarTitle,
                        showBackArrow = appBarShowBackArrow,
                        onBackArrowPressed = {
                            navController.popBackStack()
                        },
                        showSettingsAction = appBarShowSettingsAction,
                        onSettingsActionClick = {
                            navController.navigateToSettingsScreen(
                                appState.nutritionPreferences,
                                appState.filterPreferences
                            )
                        }
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.snackBarHostState
                )
            }
        ) { innerPadding ->
            AppNavHost(
                navController,
                appViewModel,
                startDestination,
                modifier = Modifier.padding(innerPadding)
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