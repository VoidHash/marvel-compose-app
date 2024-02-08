package com.andrepassos.marvelheroes.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrepassos.marvelheroes.network.api.MarvelApiRepository
import com.andrepassos.marvelheroes.network.connectivity.ConnectivityMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: MarvelApiRepository,
    connectivityMonitor: ConnectivityMonitor
): ViewModel() {

    val result = repository.characters
    val queryText = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)
    val characterDetails = repository.characterDetails
    val networkAvailable = connectivityMonitor

    init {
        getAllCharacter()
    }

    @OptIn(FlowPreview::class)
    private fun getAllCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { validateQuery(it) }
                .debounce( 2000)
                .collect {
                    repository.query(it)
                }
        }
    }

    private fun validateQuery(query: String): Boolean = query.length >= 3

    fun onQueryUpdate(input: String) {
        queryText.value = input
        queryInput.trySend(input)
    }

    fun getSingleCharacter(id: Int) {
        repository.getSingleCharacter(id)
    }


}