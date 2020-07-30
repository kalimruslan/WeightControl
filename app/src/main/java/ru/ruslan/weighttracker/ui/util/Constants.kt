package ru.ruslan.weighttracker.ui.util

import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.ui.MainActivity

object Constants {
    const val VIDEO_PLAYLIST = "PLh13OF-FPwGGKuAfUfTksOgbeqWernIkV"
    const val EXTRA_PARAM_VIDEO = "video"

    const val LOGTAG_YOUTUBE_PLAYER = "logtag_youtubePlayer"

    const val INTENT_TYPE_IMAGE = "image/*"
    const val RESULT_CAMERA = 501
    const val RESULT_GALLERY = 502

    const val KEY_WEIGHT_MEASURE = "weight_measure"
    const val KEY_HEIGHT_MEASURE = "height_measure"

    const val BEFORE_PHOTO_RESULT = 10
    const val AFTER_PHOTO_RESULT = 20

    const val SORT_DATE = "sort_date"
    const val SORT_WEIGHT = "sort_weight"
    const val SORT_PHOTO = "sort_photo"

    lateinit var APP_ACTIVITY: MainActivity
    lateinit var THIS_APP: MainApplication
}