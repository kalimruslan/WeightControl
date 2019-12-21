package ru.ruslan.weighttracker.ui.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, this, duration).show()
}
fun String.printLog(logTag: String, what: Int){
    when(what){
        Log.VERBOSE -> Log.v(logTag, this)
        Log.INFO -> Log.i(logTag, this)
        Log.WARN -> Log.w(logTag, this)
        Log.ERROR -> Log.e(logTag, this)
        else -> Log.d(logTag, this)
    }
}