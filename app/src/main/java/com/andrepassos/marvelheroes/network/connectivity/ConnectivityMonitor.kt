package com.andrepassos.marvelheroes.network.connectivity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnectivityMonitor private constructor(context: Context): ConnectivityObservable {

    companion object : SingletonHolder<ConnectivityMonitor, Context>(::ConnectivityMonitor)

    @SuppressLint("ServiceCast")
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObservable.Status> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch { send(ConnectivityObservable.Status.AVALIABLE) }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch { send(ConnectivityObservable.Status.UNAVALIABLE) }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        if (connectivityManager.activeNetwork == null)
            launch { send(ConnectivityObservable.Status.UNAVALIABLE) }

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}


open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}