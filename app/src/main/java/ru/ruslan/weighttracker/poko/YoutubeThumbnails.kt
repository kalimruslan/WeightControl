package ru.ruslan.weighttracker.poko

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class YoutubeThumbnails(
        @field:Json(name = "default") val default: YoutubeThumbnails?,
        @field:Json(name = "medium") val medium: YoutubeThumbnails?,
        @field:Json(name = "url") val url: String,
        @field:Json(name = "width") val width: String,
        @field:Json(name = "height") val height: String
                            ) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(YoutubeThumbnails::class.java.classLoader),
            parcel.readParcelable(YoutubeThumbnails::class.java.classLoader),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(default, flags)
        parcel.writeParcelable(medium, flags)
        parcel.writeString(url)
        parcel.writeString(width)
        parcel.writeString(height)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<YoutubeThumbnails> {
        override fun createFromParcel(parcel: Parcel): YoutubeThumbnails {
            return YoutubeThumbnails(parcel)
        }

        override fun newArray(size: Int): Array<YoutubeThumbnails?> {
            return arrayOfNulls(size)
        }
    }
}
