package dev.atick.network.utils

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject


class NetworkUtilsImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkUtils {
    override val currentState: Flow<NetworkState>
        get() = callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkState.CONNECTED)
                    Timber.i("NETWORK CONNECTED")
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    trySend(NetworkState.LOSING)
                    Timber.i("LOSING NETWORK CONNECTION ... ")
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkState.LOST)
                    Timber.i("NETWORK CONNECTION LOST")
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(NetworkState.UNAVAILABLE)
                    Timber.i("NETWORK UNAVAILABLE")
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    Timber.i("NETWORK TYPE CHANGED")
                }

                override fun onLinkPropertiesChanged(
                    network: Network,
                    linkProperties: LinkProperties
                ) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    Timber.i("LINK PROPERTIES CHANGED")
                }

                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                    Timber.i("BLOCKED STATUS CHANGED")
                }

            }
            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
}