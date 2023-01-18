package dev.atick.jetpack.repository

import android.net.Uri
import dev.atick.network.datasource.HemaCareDataSource
import javax.inject.Inject

class HemaCareRepositoryImpl @Inject constructor(
    private val hemaCareDataSource: HemaCareDataSource
) : HemaCareRepository {

    private lateinit var _patientId: String
    override val patientId: String
        get() = _patientId

    private val _imageUris = mutableListOf<Uri>()
    override val imageUris: List<Uri>
        get() = _imageUris

    private lateinit var _recordingUri: Uri
    override val recordingUri: Uri
        get() = _recordingUri

    override fun getImageUrisAsString(): String {
        return imageUris.map { it.path ?: "NULL" }.reduce { old, new -> "$old\n$new" }
    }

    override fun getRecordingUriAsString(): String {
        return recordingUri.path ?: "NULL"
    }

    override fun setImageUris(imageUris: List<Uri>) {
        this._imageUris.apply {
            clear()
            addAll(imageUris)
        }
    }

    override fun setRecordingUri(recordingUri: Uri) {
        this._recordingUri = recordingUri
    }

    override suspend fun upload(): Boolean {
        return hemaCareDataSource.upload(patientId, recordingUri, *imageUris.toTypedArray())
    }

    override fun setPatientId(id: String) {
        _patientId = id
    }
}