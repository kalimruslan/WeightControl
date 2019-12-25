package ru.ruslan.weighttracker.ui.profile

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_profile_current_height.*
import kotlinx.android.synthetic.main.content_profile_current_weight.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.profile.vm.ProfileViewModel
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {

    private var profileViewModel: ProfileViewModel? = null
    @Inject lateinit var viewModelFatory: ViewModelProvider.Factory
    private var carouselHeightLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
    private var carouselWeightLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
    private val dataHeight = (140..210).toList().map { it.toString() } as ArrayList<String>
    private val dataWeight = (40..150).toList().map { it.toString() } as ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initVars()
        initViews()
        setListeners()
        //observerLiveData()
    }

    private fun initViews() {
        carouselHeightLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        carouselHeightLayoutManager.maxVisibleItems = 3
        rv_curr_height.layoutManager = carouselHeightLayoutManager
        rv_curr_height.adapter = SliderAdapter().apply {
            setData(dataHeight)
            /*object : SliderAdapter.OnItemClickListener {
                override fun onItemClick(view: View) {
                    rv_curr_height.smoothScrollToPosition(rv_curr_height.getChildLayoutPosition(view))
                }
            }*/
        }

        carouselWeightLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        carouselWeightLayoutManager.maxVisibleItems = 3
        rv_curr_weight.layoutManager = carouselWeightLayoutManager
        rv_curr_weight.adapter = SliderAdapter().apply {
            setData(dataWeight)
        }
    }

    private fun initVars() {
        profileViewModel = ViewModelProviders.of(this, viewModelFatory).get(ProfileViewModel::class.java)
    }

    private fun setListeners() {
        rv_curr_height.addOnScrollListener(CenterScrollListener())
        rv_curr_weight.addOnScrollListener(CenterScrollListener())

        DefaultChildSelectionListener.initCenterItemListener({ rv, _, v ->
            val position = rv.getChildLayoutPosition(v)
        }, rv_curr_height, carouselHeightLayoutManager)

        DefaultChildSelectionListener.initCenterItemListener({ rv, _, v ->
            val position = rv.getChildLayoutPosition(v)
        }, rv_curr_weight, carouselWeightLayoutManager)
    }

    private fun observerLiveData() {
        // test
        profileViewModel?.generateAndSetProfileData()
    }


}


