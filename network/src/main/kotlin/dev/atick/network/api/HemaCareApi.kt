package dev.atick.network.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HemaCareApi {
    companion object {
        const val BASE_URL = "http://hemacare.qu-mlg.com/"
    }

    @POST("/hemaApi")
    @Multipart
    suspend fun upload(
        @Part id: MultipartBody.Part,
        @Part files: List<MultipartBody.Part>
    )
}