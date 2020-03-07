package ru.ruslan.weighttracker.ui.util

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.R

class FragmentNavigator(private val activity: Activity, private val fragmentManager: FragmentManager) {

    private val app = activity.application as MainApplication

}