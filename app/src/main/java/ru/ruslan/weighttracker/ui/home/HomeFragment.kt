package ru.ruslan.weighttracker.ui.home

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.content_home_photos.*
import kotlinx.android.synthetic.main.dialog_choose_camera_or_gallery.*
import kotlinx.android.synthetic.main.home_fragment.*
import android.content.Intent
import android.app.Activity.RESULT_OK
import android.graphics.Bitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.home.vm.HomeViewModel
import ru.ruslan.weighttracker.ui.home.vm.ProfileUI
import ru.ruslan.weighttracker.ui.util.Constants
import ru.ruslan.weighttracker.ui.util.ImageUtil
import ru.ruslan.weighttracker.ui.util.PermissionUtils
import ru.ruslan.weighttracker.ui.util.RouterUtil

class HomeFragment : Fragment() {

    private lateinit var glideOptions: RequestOptions
    private lateinit var fabAnimOpen: Animation
    private lateinit var fabAnimClose: Animation
    private lateinit var fabAnimClock: Animation
    private lateinit var fabAnimAntiClock: Animation
    private var homeContext: Context? = null
    private var chooseDialog: Dialog? = null

    private lateinit var homeViewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeContext = context
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(container?.context).inflate(R.layout.home_fragment, container, false)
        initVars()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
        observerLiveData()
    }

    private fun observerLiveData() {
        homeViewModel.openCloseFabLd.observe(this, Observer(::openCloseFabMenu))
        homeViewModel.profileLiveData.observe(this, Observer(::updateProfileViews))
    }

    private fun initVars() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

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

    private fun initViews() {
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

    private fun updateProfileViews(profile: ProfileUI?){
        profile?.let { prof ->
            tv_date_before.text = prof.dateBefore
            tv_date_after.text = prof.dateAfter
            tv_weight_before.text = prof.weigthBefore
            tv_weight_after.text = prof.weigthAfter
        }
    }

    private fun initChooseAppDialog() {
        chooseDialog = homeContext?.let { Dialog(it, R.style.AppTheme) }
        chooseDialog?.apply {
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setGravity(Gravity.BOTTOM)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(R.layout.dialog_choose_camera_or_gallery)
            llCamera.setOnClickListener{tryOpenCamera()}
            llGallery.setOnClickListener{tryOpenGallery()}
            iv_choose_close.setOnClickListener{this.dismiss()}
        }
    }

   private fun setListeners() {
        fab_main.setOnClickListener{ homeViewModel.handleFabMain() }
        fab_photo.setOnClickListener{showChooseDialog()}
    }

    private fun openCloseFabMenu(isOpen: Boolean){
        if(isOpen){
            fab_photo.startAnimation(fabAnimOpen)
            fab_weight.startAnimation(fabAnimOpen)
            fab_main.startAnimation(fabAnimClock)
            fab_photo.isClickable = true
            fab_photo.isClickable = true
        }
        else{
            fab_photo.startAnimation(fabAnimClose)
            fab_weight.startAnimation(fabAnimClose)
            fab_main.startAnimation(fabAnimAntiClock)
            fab_photo.isClickable = false
            fab_photo.isClickable = false
        }
    }

    private fun showChooseDialog() {
        chooseDialog?.show()
    }

    private fun tryOpenCamera() {
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

    private fun tryOpenGallery() {
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

    private fun setAfterImageView(bitmap: Bitmap?) {
        iv_photo_after.setImageBitmap(bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.RESULT_CAMERA && resultCode == RESULT_OK && data != null){
            homeContext?.let {setAfterImageView(ImageUtil.convertUriToBitmap(homeContext, data.data))}
        }
        else if(requestCode == Constants.RESULT_GALLERY && resultCode == RESULT_OK && data != null){
            homeContext?.let {setAfterImageView(data.extras?.get("data") as Bitmap) }
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