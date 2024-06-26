package com.matttax.foodordering.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NetworkConnectivityProvider @Inject constructor (
    @ApplicationContext context: Context
) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun hasInternet(): Boolean {
        connectivityManager.run {
            return connectivityManager.activeNetworkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            } ?: false
        }
    }

    val networkStatus: Flow<ConnectionState> = callbackFlow {
        val connectivityCallback = ConnectivityCallback(
            onUnknown = { trySend(ConnectionState.UNAVAILABLE) },
            onAvailable = { trySend(ConnectionState.AVAILABLE) },
            onLost = { trySend(ConnectionState.LOST) }
        )
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(request, connectivityCallback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        }
    }
    .distinctUntilChanged()
    .flowOn(Dispatchers.IO)
}
