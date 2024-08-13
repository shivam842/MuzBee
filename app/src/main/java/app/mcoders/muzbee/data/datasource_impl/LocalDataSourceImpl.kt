package app.mcoders.muzbee.data.datasource_impl

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import app.mcoders.muzbee.data.datasource.LocalDataSource
import app.mcoders.muzbee.data.models.MusicFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class LocalDataSourceImpl @Inject constructor(
    private val context: Context
) : LocalDataSource {

    override suspend fun fetchMusicFiles(ids: List<String>): Flow<List<MusicFile>> = flow {
        var musicFiles = listOf<MusicFile>()

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )?.use { cursor ->
            musicFiles = cursor.toMusicFile(context)
        }

        emit(musicFiles)
    }.flowOn(Dispatchers.IO) // Offload work to IO thread
}

private fun Cursor.toMusicFile(context: Context): List<MusicFile> {
    val idColumn = this.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
    //val displayNameColumn = this.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
    val titleColumn = this.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
    val artistColumn = this.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
    val albumColumn = this.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
    val dataColumn = this.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
    val durationColumn = this.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

    val musicFiles = mutableListOf<MusicFile>()

    while (this.moveToNext()) {
        val id = this.getLong(idColumn)
        //val displayName = this.getString(displayNameColumn)
        val title = this.getString(titleColumn)
        val artist = this.getString(artistColumn)
        val album = this.getString(albumColumn)
        val data = this.getString(dataColumn)
        val duration = this.getLong(durationColumn)
        val uri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            id
        )
        val coverBytes = try {
            MediaMetadataRetriever().apply {
                setDataSource(context, uri)
            }.embeddedPicture
        } catch (e: Exception) {
            null
        }
        val songCover: Bitmap? = if (coverBytes != null)
            BitmapFactory.decodeByteArray(coverBytes, 0, coverBytes.size) else null

        musicFiles.add(MusicFile(id, uri, title, title, artist, album, data, duration, songCover))
    }
    return musicFiles
}
