package com.andrepassos.marvelheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andrepassos.marvelheroes.ui.theme.MarvelHeroesTheme
import com.andrepassos.marvelheroes.view.CharacterBottomNav
import com.andrepassos.marvelheroes.view.CharacterDetailScreen
import com.andrepassos.marvelheroes.view.CollectionScreen
import com.andrepassos.marvelheroes.view.LibraryScreen
import com.andrepassos.marvelheroes.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    object Library: Destination("library")
    object Collection: Destination("collection")
    data object CharacterDetail: Destination("character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val libraryViewModel by viewModels<LibraryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelHeroesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController = navController, viewModel = libraryViewModel)
                }
            }
        }
    }
}

@Composable
private fun CharactersScaffold(navController: NavHostController, viewModel: LibraryViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { CharacterBottomNav(navHostController = navController)}
    ) { paddingValues ->
        paddingValues.apply {  }
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(navController, viewModel = viewModel, paddingValues = paddingValues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) {navBackStackEntry ->
                //CharacterDetailScreen()
            }
        }
    }
}