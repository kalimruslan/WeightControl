package ru.ruslan.weighttracker.home

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.content_home_photos.*
import kotlinx.android.synthetic.main.home_fragment.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.home.contract.HomeContract

class HomeFragment : Fragment(), HomeContract.View {

    private var presenter: HomeContract.Presenter? = null
    private lateinit var glideOptions: RequestOptions
    private lateinit var fabAnimOpen: Animation
    private lateinit var fabAnimClose: Animation
    private lateinit var fabAnimClock: Animation
    private lateinit var fabAnimAntiClock: Animation
    private var homeContext: Context? = null

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

        Glide.with(this)
            .load(R.drawable.test)
            .apply(glideOptions)
            .into(iv_photo_before)

        Glide.with(this)
            .load(R.drawable.test)
            .apply(glideOptions)
            .into(iv_photo_after)
    }

    override fun setListeners() {
        fab_main.setOnClickListener{
            presenter?.mainFabViewClicked()
        }
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
}