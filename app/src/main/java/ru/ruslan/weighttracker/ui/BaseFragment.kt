package ru.ruslan.weighttracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment(private val layout: Int) : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return LayoutInflater.from(container?.context)
            .inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVars()
    }

    abstract fun initVars()

    abstract fun initDagger()
}