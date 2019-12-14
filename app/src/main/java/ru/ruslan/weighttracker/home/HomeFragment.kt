package ru.ruslan.weighttracker.home

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.content_home_photos.*
import kotlinx.android.synthetic.main.dialog_choose_camera_or_gallery.*
import kotlinx.android.synthetic.main.home_fragment.*
import ru.ruslan.weighttracker.home.contract.HomeContract
import android.content.Intent
import androidx.core.app.ActivityCompat
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.util.Constants
import ru.ruslan.weighttracker.util.PermissionUtils
import ru.ruslan.weighttracker.util.RouterUtil
import ru.ruslan.weighttracker.util.showToast

class HomeFragment : Fragment(), HomeContract.View {

    private var presenter: HomeContract.Presenter? = null
    private lateinit var glideOptions: RequestOptions
    private lateinit var fabAnimOpen: Animation
    private lateinit var fabAnimClose: Animation
    private lateinit var fabAnimClock: Animation
    private lateinit var fabAnimAntiClock: Animation
    private var homeContext: Context? = null
    private var chooseDialog: Dialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeContext = context
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(container?.context).inflate(R.layout.home_fragment, container, false)
        presenter = HomePresenterImpl()
        presenter?.setView(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.init()
    }

    override fun initVars() {
        glideOptions = RequestOptions()
        glideOptions.apply {
            fitCenter()
            transform(RoundedCornersTransformation(30, 10))
            placeholder(R.drawable.img_placeholder)
            error(R.drawable.img_placeholder)
            fallback(R.drawable.img_placeholder)
        }

        fabAnimOpen = AnimationUtils.loadAnimation(homeContext, R.anim.fab_open)
        fabAnimClose = AnimationUtils.loadAnimation(homeContext, R.anim.fab_close)
        fabAnimClock = AnimationUtils.loadAnimation(homeContext, R.anim.fab_rotate_clock)
        fabAnimAntiClock = AnimationUtils.loadAnimation(homeContext, R.anim.fab_rotate_anticlock)
    }

    override fun initViews() {
        tv_date_before.text = "1 января 2019"
        tv_date_after.text = "1 февраля 2019"
        tv_weight_before.text = "120 кг"
        tv_weight_after.text = "110 кг"

        initChooseAppDialog()

        Glide.with(this)
            .load(R.drawable.test)
            .apply(glideOptions)
            .into(iv_photo_before)

        Glide.with(this)
            .load(R.drawable.test)
            .apply(glideOptions)
            .into(iv_photo_after)
    }

    private fun initChooseAppDialog() {
        chooseDialog = homeContext?.let { Dialog(it, R.style.AppTheme) }
        chooseDialog?.apply {
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setGravity(Gravity.BOTTOM)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(R.layout.dialog_choose_camera_or_gallery)
            llCamera.setOnClickListener{presenter?.cameraAppClickedInChooseDialog()}
            llGallery.setOnClickListener{presenter?.galleryAppClickedInChooseDialog()}
            iv_choose_close.setOnClickListener{this.dismiss()}
        }
    }

    override fun setListeners() {
        fab_main.setOnClickListener{
            presenter?.mainFabViewClicked()
        }
        fab_photo.setOnClickListener{presenter?.photoFabViewClicked()}
    }

    override fun closeFabMenu() {
        fab_photo.startAnimation(fabAnimClose)
        fab_weight.startAnimation(fabAnimClose)
        fab_main.startAnimation(fabAnimAntiClock)
        fab_photo.isClickable = false
        fab_photo.isClickable = false
    }

    override fun openFabMenu() {
        fab_photo.startAnimation(fabAnimOpen)
        fab_weight.startAnimation(fabAnimOpen)
        fab_main.startAnimation(fabAnimClock)
        fab_photo.isClickable = true
        fab_photo.isClickable = true
    }

    override fun showChooseDialog() {
        chooseDialog?.show()
    }

    override fun tryOpenCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PermissionUtils.checkAndRequestCameraPermissions(homeContext)){
                RouterUtil.openCamera(this)
                chooseDialog?.dismiss()
            }
        }
        else{
            RouterUtil.openCamera(this)
            chooseDialog?.dismiss()
        }
    }

    override fun tryOpenGallery() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PermissionUtils.checkAndRequestExternalPermission(homeContext)){
                RouterUtil.openGallery(this)
                chooseDialog?.dismiss()
            }
        }
        else{
            RouterUtil.openGallery(this)
            chooseDialog?.dismiss()
        }
    }

    override fun setAfterImageView(bitmap: Bitmap?) {
        iv_photo_after.setImageBitmap(bitmap)
    }

    override fun populateWeightAdapter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorToast(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.RESULT_CAMERA && resultCode == RESULT_OK && data != null){
            homeContext?.let { presenter?.resultFromCameraSuccess(it, data) }
        }
        else if(requestCode == Constants.RESULT_GALLERY && resultCode == RESULT_OK && data != null){
            homeContext?.let { presenter?.resultFromGallerySuccess(it, data) }
        }
    }

    // TODO Что-то это не срабатывает
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PermissionUtils.CODE_CAMERA_PERMISSION){
            if(grantResults.isNotEmpty()){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) tryOpenCamera()
            }
        }
        else if(requestCode == PermissionUtils.CODE_EXTERNAL_PERMISSION){
            if(grantResults.isNotEmpty()){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) tryOpenGallery()
            }
        }
    }
}