package com.dzul.notestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.dzul.notestapp.ui.screens.notes.NoteMainScreen
import com.dzul.notestapp.ui.screens.notes.child.AddNotesScreen
import com.dzul.notestapp.ui.screens.notes.child.DetailNoteScreen
import com.dzul.notestapp.ui.screens.notes.child.EditNoteScreen
import com.dzul.notestapp.ui.screens.profile.ProfileMainScreen
import com.dzul.notestapp.ui.theme.NotestappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesApp()
        }
    }
}

sealed class MainScreenRoute(val route: String) {
    data object Note: MainScreenRoute("/notes")
    data object Profile: MainScreenRoute("/profiles")
}

enum class BottomNavBarRoutes(val route: String, val redirectRoute: String, @StringRes val resourceId: Int, val icon: ImageVector = Icons.Filled.Warning) {
    Note("/nav-route", MainScreenRoute.Note.route, R.string.notes_main_screen, Icons.Filled.List),
    Profile("/nav-profile", MainScreenRoute.Profile.route, R.string.profile_screen, Icons.Filled.Person)
}

enum class NoteChildRoutes(val route: String) {
    Add("${MainScreenRoute.Note.route}/add"),
    Detail("${MainScreenRoute.Note.route}/detail"),
    Edit("${MainScreenRoute.Note.route}/edit")
}

@Composable
fun NotesApp() {
    NotestappTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()
            val screenItems = listOf(BottomNavBarRoutes.Note, BottomNavBarRoutes.Profile)
            Scaffold(
                bottomBar = {
                    if(appState.shouldShowBottomNavBar) {
                        NavigationBar {
                            val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            screenItems.forEach { screenItem ->
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == screenItem.route } == true,
                                    onClick = {
                                        appState.navHostController.navigate(screenItem.route)
                                        {
                                            popUpTo(appState.navHostController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(imageVector = screenItem.icon, contentDescription = screenItem.route) },
                                    label = { Text(text = stringResource(id = screenItem.resourceId)) }
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = appState.navHostController,
                    startDestination = BottomNavBarRoutes.Note.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    navigation(
                        route = BottomNavBarRoutes.Note.route,
                        startDestination = MainScreenRoute.Note.route
                    ) {
                        composable(MainScreenRoute.Note.route) {
                            NoteMainScreen(appState.navHostController)
                        }
                        composable(NoteChildRoutes.Add.route) {
                            AddNotesScreen(appState.navHostController)
                        }
                        composable("${NoteChildRoutes.Detail.route}/{noteId}") { backStackEntry ->
                            backStackEntry.arguments?.getString("noteId");
                            DetailNoteScreen(
                                navHostController = appState.navHostController
                            )
                        }
                        composable("${NoteChildRoutes.Edit.route}/{noteId}") { backStackEntry ->
                            backStackEntry.arguments?.getString("noteId");
                            EditNoteScreen(
                                navController = appState.navHostController
                            )
                        }
                    }

                    navigation(
                        route = BottomNavBarRoutes.Profile.route,
                        startDestination = MainScreenRoute.Profile.route
                    ) {
                        composable(MainScreenRoute.Profile.route) {
                            ProfileMainScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    navHostController: NavHostController = rememberNavController()
) = remember(navHostController) {
    AppState(navHostController)
}

class AppState(
    val navHostController: NavHostController
) {

    private val routes = BottomNavBarRoutes.entries.map { it.redirectRoute }

    val shouldShowBottomNavBar: Boolean
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination?.route in routes
}