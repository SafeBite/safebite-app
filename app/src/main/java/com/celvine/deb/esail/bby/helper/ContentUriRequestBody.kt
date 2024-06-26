import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.lang.IllegalStateException

class ContentUriRequestBody(
    private val contentResolver: ContentResolver,
    private val contentUri: Uri
) : RequestBody() {

    override fun contentType(): MediaType? {
        val contentType = contentResolver.getType(contentUri) ?: return null
        return contentType.toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        val inputStream = contentResolver.openInputStream(contentUri)
            ?: throw IllegalStateException("Couldn't open content URI for reading: $contentUri")

        inputStream.source().use { source ->
            sink.writeAll(source)
        }
    }
}