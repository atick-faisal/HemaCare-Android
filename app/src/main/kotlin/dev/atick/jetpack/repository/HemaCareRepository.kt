package dev.atick.jetpack.repository

import android.net.Uri

interface HemaCareRepository {

    fun getImageUris(): String
    fun getRecordingUri(): String

    fun setImageUris(imageUris: List<Uri>)
    fun setRecordingUri(recordingUri: Uri)
    suspend fun upload()

}