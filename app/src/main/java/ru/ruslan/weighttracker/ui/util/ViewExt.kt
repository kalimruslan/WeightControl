package ru.ruslan.weighttracker.ui.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.util.Constants.APP_ACTIVITY
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun AppCompatImageView.loadImage(imageUri: String = "", drawableId: Int = 0) {
    Glide.with(context)
        .load(File(imageUri))
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .fallback(R.drawable.img_placeholder)
        .fitCenter()
        .transform(RoundedCornersTransformation(30, 10))
        .into(this)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

inline fun <reified T : Activity> Activity.startActivityExt(context: Context) {
    startActivity(Intent(context, T::class.java))
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true, title: String) {
    if(addStack){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fl_container, fragment)
            .commit()
    } else{
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
    supportActionBar?.title = title
}

inline fun <reified T: Activity> Fragment.startActivityForResultExt(context: Context, resultCode: Int){
    val intent: Intent = Intent(context, T::class.java)
    startActivityForResult(intent, resultCode)
}

internal fun Float.toDP(): Float {
    val displayMetrics = APP_ACTIVITY.resources.displayMetrics
    return (this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt().toFloat()
}

