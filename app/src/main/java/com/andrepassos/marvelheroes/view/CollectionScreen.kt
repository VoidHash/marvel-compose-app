package com.andrepassos.marvelheroes.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andrepassos.marvelheroes.util.UiUtil
import com.andrepassos.marvelheroes.viewmodel.CollectionViewModel

@Composable
fun CollectionScreen(collectionViewModel: CollectionViewModel, navHostController: NavHostController) {

    val characterInCollection = collectionViewModel.collection.collectAsState()
    val expandedElement = remember { mutableIntStateOf(-1) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(characterInCollection.value) {character ->
            Column {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .height(100.dp)
                    .padding(4.dp)
                    .clickable {
                        if (expandedElement.intValue == character.id) {
                            expandedElement.intValue = -1
                        } else {
                            expandedElement.intValue = character.id
                        }
                    }
                ) {
                    UiUtil.CharacterImage(
                        contentScale = ContentScale.FillHeight,
                        url = character.thumbnail,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                    )

                    Column(modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .fillMaxHeight()
                    ) {
                        Text(
                            text = character.name ?: "No name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            maxLines = 3
                        )
                        Text(
                            text = character.comics ?: "",
                            fontStyle = FontStyle.Italic
                        )
                    }

                    Column( verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(4.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                collectionViewModel.deleteCharacter(character)
                            }
                        )

                        if (character.id == expandedElement.intValue) {
                            Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = null)
                        } else {
                            Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
                        }
                    }
                }
            }
            Divider(
                color = Color.LightGray,
                modifier = Modifier.padding(
                    top = 4.dp, bottom = 4.dp, start = 20.dp, end = 20.dp
                )
            )
        }
    }
}