package com.andrepassos.marvelheroes.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

object UiUtil {

    @Composable
    fun AttributionText(text: String?) {
        if (text != null) {
            Text(text = text, modifier = Modifier.padding(start = 8.dp, top = 4.dp), fontSize = 12.sp)
        }
    }

    @Composable
    fun CharacterImage(
        url: String?,
        modifier: Modifier,
        contentScale: ContentScale = ContentScale.FillHeight
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}