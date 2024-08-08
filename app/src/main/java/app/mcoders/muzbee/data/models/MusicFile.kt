package app.mcoders.muzbee.data.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.io.ByteArrayOutputStream

data class MusicFile(
    val id: Long,
    val uri: Uri,
    val displayName: String,
    val title: String,
    val artist: String,
    val album: String,
    val data: String,
    val duration: Long,
    val artWork: Bitmap?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readParcelable(Uri::class.java.classLoader) ?: Uri.EMPTY,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        BitmapFactory.decodeByteArray(parcel.createByteArray(), 0, parcel.readInt())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeParcelable(uri, flags)
        parcel.writeString(displayName)
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeString(album)
        parcel.writeString(data)
        parcel.writeLong(duration)

        val byteArrayOutputStream = ByteArrayOutputStream()
        artWork?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        parcel.writeInt(byteArray.size)
        parcel.writeByteArray(byteArray)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MusicFile> {
        override fun createFromParcel(parcel: Parcel): MusicFile {
            return MusicFile(parcel)
        }

        override fun newArray(size: Int): Array<MusicFile?> {
            return arrayOfNulls(size)
        }
    }
}