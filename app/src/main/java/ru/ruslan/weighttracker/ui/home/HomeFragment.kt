package ru.ruslan.weighttracker.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.content_home_photos.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_home_weight.*
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.ui.util.Constants
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.domain.contract.HomeContract
import ru.ruslan.weighttracker.ui.camera.CameraActivity
import ru.ruslan.weighttracker.ui.util.*
import javax.inject.Inject

@HomeScope
class HomeFragment : Fragment(), HomeContract.VIew, WeightAdapter.WeightItemClickListener {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    private var weightAdapter: WeightAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context?.applicationContext as MainApplication).getAppComponent().getHomeComponent()
            .create().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context)
            .inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        presenter.getWeightList()
        presenter.getSavedObjects(activity?.cacheDir!!)
    }

    override fun initViews() {
        iv_photo_before.loadImage(drawableId = R.drawable.img_placeholder)
        iv_photo_after.loadImage(drawableId = R.drawable.img_placeholder)

        weightAdapter =  WeightAdapter(this@HomeFragment)

        rv_my_weight.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(rv_my_weight.context, DividerItemDecoration.VERTICAL))
            adapter = weightAdapter
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
    }

    override fun updatePictureViews(profile: HomeUI?,
                                    requestCode: Int) {
        profile?.let {
            when (requestCode) {
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

    override fun errorForLoadingWeightList(errorText: String) {
        context?.let { errorText.showToast(it) }
    }

    override fun populateWeightAdapter(weights: List<HomeUI>?) {
        weights?.let { weightAdapter?.setList(it) }
    }

    override fun startCameraScreen(needResult: Boolean, requestCode: Int) {
        if (needResult) {
            when (requestCode) {
                Constants.BEFORE_PHOTO_RESULT -> {
                    this.startActivityForResultExt<CameraActivity>(
                        context!!,
                        Constants.BEFORE_PHOTO_RESULT)
                }
                Constants.AFTER_PHOTO_RESULT -> {
                    this.startActivityForResultExt<CameraActivity>(
                        context!!,
                        Constants.AFTER_PHOTO_RESULT)
                }
            }
        } else activity?.startActivityExt<CameraActivity>(context!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constants.BEFORE_PHOTO_RESULT ->
                    presenter.getDataForPicture(Constants.BEFORE_PHOTO_RESULT, activity?.cacheDir!!)
                Constants.AFTER_PHOTO_RESULT ->
                    presenter.getDataForPicture(Constants.AFTER_PHOTO_RESULT, activity?.cacheDir!!)
            }
        }
    }

    override fun weightItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}