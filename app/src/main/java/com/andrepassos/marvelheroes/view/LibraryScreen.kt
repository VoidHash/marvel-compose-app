package com.andrepassos.marvelheroes.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andrepassos.marvelheroes.Destination
import com.andrepassos.marvelheroes.model.CharacterApiResponse
import com.andrepassos.marvelheroes.network.api.NetworkResult
import com.andrepassos.marvelheroes.util.UiUtil.AttributionText
import com.andrepassos.marvelheroes.util.UiUtil.CharacterImage
import com.andrepassos.marvelheroes.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(
    navController: NavHostController,
    viewModel: LibraryViewModel,
    paddingValues: PaddingValues
) {
    val result by viewModel.result.collectAsState()
    val text = viewModel.queryText.collectAsState()


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = text.value, 
            onValueChange = viewModel::onQueryUpdate, 
            label = { Text(text = "Character search") },
            placeholder = { Text(text = "Character") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            when(result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for a character")
                }
                is NetworkResult.Success -> {
                    ShowCharactersList(result, navController)
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }

        }
    }
}

@Composable
fun ShowCharactersList(
    result: NetworkResult<CharacterApiResponse>,
    navController: NavHostController
) {
    result.data?.characterData?.results.let { characterResults ->
        LazyColumn(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Top
        ) {
            result.data?.attributionText.let {
                item {
                    AttributionText(text = it)
                }
            }

            if (characterResults != null) {
                items(characterResults) { character ->
                    val imageUrl = character?.thumbnail?.path+ "." + character?.thumbnail?.extension
                    val title = character?.name
                    val description = character?.description
                    val context = LocalContext.current
                    val id = character?.id

                    Column(modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            if (character?.id != null) {
                                navController.navigate(Destination.CharacterDetail.createRoute(id))
                            } else {
                                Toast
                                    .makeText(context, "Character id is null", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            CharacterImage(
                                url = imageUrl,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(100.dp)
                            )
                            Column(modifier = Modifier.padding(4.dp)) {
                                Text(text = title ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                        Text(text = description ?: "", maxLines = 5, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
