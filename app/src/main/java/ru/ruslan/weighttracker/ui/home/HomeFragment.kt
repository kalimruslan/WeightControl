package ru.ruslan.weighttracker.ui.home

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.content_home_photos.*
import kotlinx.android.synthetic.main.dialog_choose_camera_or_gallery.*
import kotlinx.android.synthetic.main.home_fragment.*
import androidx.fragment.app.Fragment
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.ui.util.Constants
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.domain.contract.HomeContract
import ru.ruslan.weighttracker.ui.camera.CameraActivity
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.util.*
import javax.inject.Inject

@HomeScope
class HomeFragment : Fragment(), HomeContract.VIew {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    private lateinit var fabAnimOpen: Animation
    private lateinit var fabAnimClose: Animation
    private lateinit var fabAnimClock: Animation
    private lateinit var fabAnimAntiClock: Animation
    private var chooseDialog: Dialog? = null

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as MainApplication).getAppComponent().getHomeComponent().create().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context)
            .inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
    }

    override fun showToastForUserNotExist() {
        context?.let { getString(R.string.toast_profile_not_found).showToast(it) }
    }

    override fun startProfileScreen() {
        context?.let {
            this.startActivityExt<ProfileActivity>(it)
        }
    }

    override fun initViews() {
        fabAnimOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        fabAnimClose = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        fabAnimClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock)
        fabAnimAntiClock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock)

        initChooseAppDialog()

        iv_photo_before.loadImage(drawableId = R.drawable.img_placeholder)
        iv_photo_after.loadImage(drawableId = R.drawable.img_placeholder)
    }

    private fun initChooseAppDialog() {
        chooseDialog = context?.let { Dialog(it, R.style.AppTheme) }
        chooseDialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setGravity(Gravity.BOTTOM)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(R.layout.dialog_choose_camera_or_gallery)
            llCamera.setOnClickListener { presenter.cameraViewClicked() }
            llGallery.setOnClickListener { presenter.galleryViewClicked() }
            iv_choose_close.setOnClickListener { this.dismiss() }
        }
    }

    override fun setListeners() {
        iv_photo_before.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.checkAndRequestCameraPermissions(context))
                    presenter.photoBeforeViewClicked()
            } else presenter.photoBeforeViewClicked()
        }
        iv_photo_after.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionUtils.checkAndRequestCameraPermissions(context))
                    presenter.photoAfterViewClicked()
            } else presenter.photoAfterViewClicked()

        }

        fab_main.setOnClickListener {
            presenter.fabMainViewClicked()
        }
        fab_photo.setOnClickListener {
            presenter.fabPhotoViewClicked()
        }
    }

    override fun updatePictureViews(profile: HomeUI?,
                                    requestCode: Int) {
       profile?.let {
           when(requestCode){
               Constants.BEFORE_PHOTO_RESULT -> {
                   tv_date_before.text = it.photoDate
                   tv_weight_before.text = "${it.weightOnPhoto} кг."
                   iv_photo_before.loadImage(imageUri = it.photoPath!!)
               }
               Constants.AFTER_PHOTO_RESULT -> {
                   tv_date_after.text = it.photoDate
                   tv_weight_after.text = "${it.weightOnPhoto} кг."
                   iv_photo_after.loadImage(imageUri = it.photoPath!!)
               }
           }
       }
    }

    override fun startCameraScreen(needResult: Boolean, requestCode: Int) {
        if (needResult) {
            when (requestCode) {
                Constants.BEFORE_PHOTO_RESULT -> {
                    this.startActivityForResultExt<CameraActivity>(
                        context!!,
                        Constants.BEFORE_PHOTO_RESULT
                    )
                }
                Constants.AFTER_PHOTO_RESULT -> {
                    this.startActivityForResultExt<CameraActivity>(
                        context!!,
                        Constants.AFTER_PHOTO_RESULT
                    )
                }
            }
        } else activity?.startActivityExt<CameraActivity>(context!!)
    }

    override fun openCloseFabMenu(isOpen: Boolean) {
        if (isOpen) {
            fab_photo.startAnimation(fabAnimOpen)
            fab_weight.startAnimation(fabAnimOpen)
            fab_main.startAnimation(fabAnimClock)
            fab_photo.isClickable = true
            fab_photo.isClickable = true
        } else {
            fab_photo.startAnimation(fabAnimClose)
            fab_weight.startAnimation(fabAnimClose)
            fab_main.startAnimation(fabAnimAntiClock)
            fab_photo.isClickable = false
            fab_photo.isClickable = false
        }
    }

    override fun showChooseDialog() {
        chooseDialog?.show()
    }

    override fun tryOpenCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.checkAndRequestCameraPermissions(context)) {
                startCameraScreen(false)
                chooseDialog?.dismiss()
            }
        } else {
            startCameraScreen(false)
            chooseDialog?.dismiss()
        }
    }

    override fun tryOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.checkAndRequestExternalPermission(context)) {
                RouterUtil.openGallery(this)
                chooseDialog?.dismiss()
            }
        } else {
            RouterUtil.openGallery(this)
            chooseDialog?.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.BEFORE_PHOTO_RESULT ->
                presenter.getDataForPicture(Constants.BEFORE_PHOTO_RESULT)
            Constants.AFTER_PHOTO_RESULT ->
                presenter.getDataForPicture(Constants.AFTER_PHOTO_RESULT)
        }
    }
}