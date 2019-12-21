package ru.ruslan.weighttracker.ui.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.ruslan.weighttracker.R

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, this, duration).show()
}

fun AppCompatImageView.loadImage(imageUri: String = "", drawableId: Int = 0) {
    Glide.with(context)
        .load(if(imageUri.isNotEmpty())imageUri else drawableId)
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .fallback(R.drawable.img_placeholder)
        .fitCenter()
        .transform(RoundedCornersTransformation(30, 10))
        .into(this)
}