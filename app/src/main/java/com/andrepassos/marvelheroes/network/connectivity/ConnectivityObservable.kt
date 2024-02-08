package com.andrepassos.marvelheroes.network.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObservable {

    fun observe(): Flow<Status>

    enum class Status {
        AVALIABLE,
        UNAVALIABLE
    }
}