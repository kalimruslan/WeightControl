package ru.ruslan.weighttracker.ui.camera

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_camera.*
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.domain.contract.camera.CameraContract
import ru.ruslan.weighttracker.ui.util.FragmentNavigator
import javax.inject.Inject

class CameraActivity : AppCompatActivity(), CameraContract.View {

    lateinit var fragmentNavigator: FragmentNavigator
    @Inject lateinit var presenter: CameraContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).getAppComponent().getCameraComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setSupportActionBar(camera_toolbar)
        presenter.setView(this)
    }

    override fun initVars() {
        fragmentNavigator = FragmentNavigator(this, supportFragmentManager)
    }

    override fun openCameraPreview() {
        fragmentNavigator.navigateToCameraPreview()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
