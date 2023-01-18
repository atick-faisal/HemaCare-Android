package dev.atick.network.datasource

import android.net.Uri

interface HemaCareDataSource {
    suspend fun upload(
        id: String,
        recordingUri: Uri,
        vararg imageUris: Uri
    ): Boolean
}