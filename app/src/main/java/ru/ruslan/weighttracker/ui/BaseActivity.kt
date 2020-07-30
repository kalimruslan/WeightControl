package ru.ruslan.weighttracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val layout: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()
        super.onCreate(savedInstanceState)
        setContentView(layout)
        initMembers()
    }

    abstract fun initDagger()
    abstract fun initMembers()
}