package ru.ruslan.weighttracker.videos.list.vm.model

import android.os.Parcel
import android.os.Parcelable

class VideoUI(val title: String?,
              val description: String?,
              val url: String?,
              val channelTitle: String?,
              val publishedAt: String?,
              val videoId: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    constructor() : this("", "", "", "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(channelTitle)
        parcel.writeString(publishedAt)
        parcel.writeString(videoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoUI> {
        override fun createFromParcel(parcel: Parcel): VideoUI {
            return VideoUI(parcel)
        }

        override fun newArray(size: Int): Array<VideoUI?> {
            return arrayOfNulls(size)
        }
    }
}