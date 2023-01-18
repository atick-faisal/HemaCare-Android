package dev.atick.network.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HemaCareApi {
    companion object {
        const val BASE_URL = "http://34.133.239.54/"
    }

    @POST("/api/smart_care/store_patient_read")
    @Multipart
    suspend fun upload(
        @Part("id") id: RequestBody,
        @Part files: List<MultipartBody.Part>
    )
}