package com.example.accesibilidadapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import kotlin.coroutines.resume

class LocationHelper(private val context: Context) {

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun getCity(): String {
        if (!hasLocationPermission()) {
            return "Permiso de ubicación no concedido"
        }

        val location = getLocation() ?: return "Ubicación no disponible"

        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    address.locality
                        ?: address.subAdminArea
                        ?: address.adminArea
                        ?: address.countryName
                        ?: "Ciudad desconocida"
                } else {
                    "Ciudad desconocida"
                }
            } catch (e: Exception) {
                "Error al obtener ubicación"
            }
        }
    }

    @Suppress("MissingPermission")
    private suspend fun getLocation(): Location? {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val providers = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER,
            LocationManager.PASSIVE_PROVIDER
        )

        // Intentar primero con última ubicación conocida
        for (provider in providers) {
            val last = locationManager.getLastKnownLocation(provider)
            if (last != null) return last
        }

        // Si no hay caché, solicitar actualización activa (timeout 10s)
        return withTimeoutOrNull(10_000L) {
            suspendCancellableCoroutine { cont ->
                val enabledProvider = providers.firstOrNull { p ->
                    locationManager.isProviderEnabled(p)
                } ?: run {
                    cont.resume(null)
                    return@suspendCancellableCoroutine
                }

                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        locationManager.removeUpdates(this)
                        if (cont.isActive) cont.resume(location)
                    }
                    @Deprecated("Deprecated in Java")
                    override fun onStatusChanged(provider: String?, status: Int, extras: android.os.Bundle?) {}
                }

                locationManager.requestLocationUpdates(
                    enabledProvider,
                    0L,
                    0f,
                    listener,
                    Looper.getMainLooper()
                )

                cont.invokeOnCancellation {
                    locationManager.removeUpdates(listener)
                }
            }
        }
    }
}
