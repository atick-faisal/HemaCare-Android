package dev.atick.network.datasource

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.atick.core.utils.FileUtils
import dev.atick.network.api.HemaCareApi
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


class HemaCareDataSourceImpl @Inject constructor(
    private val hemaCareApi: HemaCareApi,
    @ApplicationContext private val context: Context
) : HemaCareDataSource {
    override suspend fun upload(id: String, recordingUri: Uri, vararg imageUris: Uri): Boolean {
        val files = mutableListOf<MultipartBody.Part>()
        return try {
            imageUris.forEachIndexed { idx, uri ->
                files.add(getMultiPartBody("image_${idx + 1}", uri))
            }
            files.add(getMultiPartBody("csv", recordingUri))

            val idPart = MultipartBody.Part.createFormData("id", id)
            hemaCareApi.upload(idPart, files)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } catch (e: HttpException) {
            e.printStackTrace()
            false
        }
    }

    private fun getMultiPartBody(name: String, uri: Uri): MultipartBody.Part {
        val file = FileUtils.getFileFromUri(context, uri)
        return MultipartBody.Part.createFormData(
            name, file.name, file.asRequestBody()
        )
    }

}