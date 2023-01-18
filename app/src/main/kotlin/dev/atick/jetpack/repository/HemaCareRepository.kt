package dev.atick.jetpack.repository

import android.net.Uri

interface HemaCareRepository {

    val imageUris: List<Uri>
    val recordingUri: Uri

    fun getImageUrisAsString(): String
    fun getRecordingUriAsString(): String

    fun setImageUris(imageUris: List<Uri>)
    fun setRecordingUri(recordingUri: Uri)
    suspend fun upload(id: String): Boolean

}