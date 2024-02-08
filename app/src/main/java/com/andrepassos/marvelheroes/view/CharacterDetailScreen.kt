package com.andrepassos.marvelheroes.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andrepassos.marvelheroes.Destination
import com.andrepassos.marvelheroes.util.DataUtil.comicsToString
import com.andrepassos.marvelheroes.util.UiUtil
import com.andrepassos.marvelheroes.viewmodel.CollectionViewModel
import com.andrepassos.marvelheroes.viewmodel.LibraryViewModel

@Composable
fun CharacterDetailScreen(
    libraryViewModel: LibraryViewModel,
    collectionViewModel: CollectionViewModel,
    paddingValues: PaddingValues,
    navHostController: NavHostController
) {
    val character = libraryViewModel.characterDetails.value
    val collection by collectionViewModel.collection.collectAsState()
    val inCollection = collection.map { it.apiId }.contains(character?.id)

    if(character == null) {
        navHostController.navigate(Destination.Library.route) {
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        collectionViewModel.setCurrentCharacterId(character?.id)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(rememberScrollState())
    ) {
        val imageUrl = character?.thumbnail?.path+"."+character?.thumbnail?.extension
        val title = character?.name ?: "No name"
        val comics = character?.comics?.items?.mapNotNull { it?.name }?.comicsToString() ?: "No comics"
        val description = character?.description ?: "No description"

        UiUtil.CharacterImage(url = imageUrl, modifier = Modifier
            .width(200.dp)
            .padding(4.dp))
        
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = comics,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.padding(4.dp)
        )

        Button(onClick = {
            if (!inCollection && character != null)
                collectionViewModel.addCharacter(character)
        }, modifier = Modifier.padding(bottom = 20.dp)) {
            if(!inCollection) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(text = "Add to collection")
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text(text = "Added")
                }
            }
        }
    }
}