package ru.ruslan.weighttracker.ui.util

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.camera.preview.CameraPreviewFragment

class FragmentNavigator(private val activity: Activity, private val fragmentManager: FragmentManager) {

    private val app = activity.application as MainApplication

    fun navigateToCameraPreview(){
        // TODO тут нужно очищать все копмоненты даггера
        changeFragment(CameraPreviewFragment.newInstance())
    }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .addToBackStack(fragment.javaClass.name)
            .replace(R.id.container_for_camera, fragment)
            .commit()
    }

    fun navigateToCameraResult() {

    }
}