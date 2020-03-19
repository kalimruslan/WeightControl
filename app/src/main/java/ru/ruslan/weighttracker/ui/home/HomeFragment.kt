package ru.ruslan.weighttracker.ui.home

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.ClipDescription
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_home_photos.*
import kotlinx.android.synthetic.main.content_home_weight.*
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.domain.contract.HomeContract
import ru.ruslan.weighttracker.ui.camera.CameraActivity
import ru.ruslan.weighttracker.ui.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


@HomeScope
class HomeFragment : Fragment(), HomeContract.VIew, WeightAdapter.WeightItemClickListener, PopupMenu.OnMenuItemClickListener {

    private val onDragListener = View.OnDragListener { view, dragEvent ->
        (view as? CardView)?.let {
            when (dragEvent.action) {
                // Once the drag event has started, we elevate all the views that are listening.
                // In our case, that's two of the areas.
                DragEvent.ACTION_DRAG_STARTED -> {
                    it.elevation = toDP(CARD_ELEVATION_DRAG_START_DP)
                    return@OnDragListener true
                }
                // Once the drag gesture enters a certain area, we want to elevate it even more.
                DragEvent.ACTION_DRAG_ENTERED -> {
                    it.elevation = toDP(CARD_ELEVATION_DRAG_ENTER_DP)
                    return@OnDragListener true
                }
                // No need to handle this for our use case.
                DragEvent.ACTION_DRAG_LOCATION -> {
                    return@OnDragListener true
                }
                // Once the drag gesture exits the area, we lower the elevation down to the previous one.
                DragEvent.ACTION_DRAG_EXITED -> {
                    it.elevation = toDP(CARD_ELEVATION_DRAG_START_DP)
                    return@OnDragListener true
                }
                // Once the color is dropped on the area, we want to paint it in that color.
                DragEvent.ACTION_DROP -> {
                    val photoId: ClipData.Item = dragEvent.clipData.getItemAt(0)
                    presenter.recyclerItemDropped(photoId.text)
                    val colorHex = photoId.text
                    it.setBackgroundColor(Color.parseColor(colorHex.toString()))
                    return@OnDragListener true
                }
                // Once the drag has ended, revert card views to the default elevation.
                DragEvent.ACTION_DRAG_ENDED -> {
                    it.elevation = toDP(CARD_ELEVATION_DEFAULT_DP)
                    return@OnDragListener true
                }
                else -> return@OnDragListener false
            }
        }
        false
    }

    @Inject
    lateinit var presenter: HomeContract.Presenter

    private var weightAdapter: WeightAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
        // Default card elevation.
        const val CARD_ELEVATION_DEFAULT_DP = 8F
        // Card elevation once the dragging has started.
        const val CARD_ELEVATION_DRAG_START_DP = 28F
        // Card elevation once the color is dragged over one of the areas.
        const val CARD_ELEVATION_DRAG_ENTER_DP = 40F
    }

    private fun toDP(px: Float): Float{
        val displayMetrics = context!!.resources.displayMetrics
        return (px * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt().toFloat()
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
        card_photo_before.setOnDragListener(onDragListener)
        card_photo_after.setOnDragListener(onDragListener)

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
        iv_more_weight_block.setOnClickListener{presenter.moreViewClicked(iv_more_weight_block)}

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

    override fun showSortingPopup(view: ImageView) {
        val wrapper: ContextWrapper = ContextThemeWrapper(context, R.style.MyPopupOtherStyle)
        val popupMenu = PopupMenu(wrapper, view)
        popupMenu.menuInflater.inflate(R.menu.sort_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        when(menuItem?.itemId){
            R.id.sortDate -> context?.let { weightAdapter?.sort(Constants.SORT_DATE)}
            R.id.sortWeight -> context?.let { weightAdapter?.sort(Constants.SORT_WEIGHT)}
            R.id.sortPhoto -> context?.let { weightAdapter?.sort(Constants.SORT_PHOTO) }
        }
        return false
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
    }

    override fun weightItemLongClicked(position: Int, it: View, homeUI: HomeUI) {
        val item = ClipData.Item(homeUI.photoId.toString())

        val dragData = ClipData(it.tag as? CharSequence, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)

        val myShadow = MyDragShadowBuilder(it)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            it.startDragAndDrop(dragData, myShadow, null, 0)
        } else {
            it.startDrag(dragData, myShadow, null, 0)
        }
    }
}