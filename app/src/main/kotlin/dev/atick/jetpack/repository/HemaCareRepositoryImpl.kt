package dev.atick.jetpack.repository

import android.net.Uri
import dev.atick.network.datasource.HemaCareDataSource
import javax.inject.Inject

class HemaCareRepositoryImpl @Inject constructor(
    private val hemaCareDataSource: HemaCareDataSource
) : HemaCareRepository {

    private val imageUris = mutableListOf<Uri>()
    private lateinit var recordingUri: Uri

    override fun getImageUris(): String {
        return imageUris.map { it.path ?: "NULL" }.reduce { old, new -> "$old\n$new" }
    }

    override fun getRecordingUri(): String {
        return recordingUri.path ?: "NULL"
    }

    override fun setImageUris(imageUris: List<Uri>) {
        this.imageUris.apply {
            clear()
            addAll(imageUris)
        }
    }

    override fun setRecordingUri(recordingUri: Uri) {
        this.recordingUri = recordingUri
    }

    override suspend fun upload() {
        TODO("Not yet implemented")
    }


}