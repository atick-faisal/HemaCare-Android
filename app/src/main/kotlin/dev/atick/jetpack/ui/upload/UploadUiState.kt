package dev.atick.jetpack.ui.upload

data class UploadUiState(
    val nSelectedImages: Int = 0,
    val nRecordedFiles: Int = 1,
    val uploadState: UploadState = UploadState.Idle
)

sealed class UploadState(val description: String) {
    object Idle : UploadState("Upload")
    object Uploading : UploadState("Uploading  ... ")
    object UploadComplete : UploadState("Done! Upload Another?")
}
