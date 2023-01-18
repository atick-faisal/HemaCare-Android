package dev.atick.jetpack.repository

import android.net.Uri

interface HemaCareRepository {

    val patientId: String
    val imageUris: List<Uri>
    val recordingUri: Uri

    fun getImageUrisAsString(): String
    fun getRecordingUriAsString(): String
    fun setImageUris(imageUris: List<Uri>)
    fun setRecordingUri(recordingUri: Uri)
    fun setPatientId(id: String)
    suspend fun upload(): Boolean

}