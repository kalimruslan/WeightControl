package ru.ruslan.weighttracker.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.content_profile_current_height.*
import kotlinx.android.synthetic.main.content_profile_current_weight.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.profile.vm.ProfileViewModel
import ru.ruslan.weighttracker.ui.profile.vm.model.ProfileUI
import ru.ruslan.weighttracker.ui.util.*
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {

    private var profileViewModel: ProfileViewModel? = null
    @Inject
    lateinit var viewModelFatory: ViewModelProvider.Factory
    private val dataHeight = (140..210).toList().map { it.toString() } as ArrayList<String>
    private val dataWeight = (40..150).toList().map { it.toString() } as ArrayList<String>
    private var heightSliderAdapter: SliderAdapter? = null
    private var weightSliderAdapter: SliderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initVars()
        initViews()
        setListeners()
        observerLiveData()
    }

    private fun initViews() {
        profile_toolbar.setNavigationIcon(R.drawable.ic_close)
        profile_toolbar.setNavigationOnClickListener {
            if(profileViewModel?.userIsExist()!!)
                startActivityExt<MainActivity>(this)
            onBackPressed()
        }
        profile_toolbar.title = resources.getString(R.string.profile)

        val padding: Int = ScreenUtil.getScreenWidth(this) / 2 - ScreenUtil.dpToPx(this, 40)

        val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter(
            this, R.layout.item_sex_exposed_dropdown_popup,
            resources.getStringArray(R.array.sex_dropdown)
        )
        auto_complete_textview_sex.setAdapter(arrayAdapter)

        heightSliderAdapter = SliderAdapter(object : SliderAdapter.OnItemCLickListener {
            @SuppressLint("SetTextI18n")
            override fun clickItem(pos: Int) {
                tv_height.text = "${dataHeight[pos]} + ${getString(R.string.text_default_height_measuer)}"
                rv_curr_height.smoothScrollToPosition(pos)
            }
        })
        rv_curr_height.apply {
            setPadding(padding, 0, padding, 0)
            layoutManager = SliderLayoutManager(context, scaleDown = true, needListener = false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("SetTextI18n")
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        tv_height.text = "${dataHeight[(recyclerView.layoutManager as SliderLayoutManager).findFirstVisibleItemPosition()]} ${getString(
                                R.string.text_default_height_measuer)}"
                }
            })
            adapter = heightSliderAdapter
        }
        heightSliderAdapter?.setData(dataHeight)


        weightSliderAdapter = SliderAdapter(object : SliderAdapter.OnItemCLickListener {
            @SuppressLint("SetTextI18n")
            override fun clickItem(pos: Int) {
                tv_weight.text = "${dataWeight[pos]} ${getString(R.string.text_default_weight_measure)}"
                rv_curr_weight.smoothScrollToPosition(pos)
            }
        })

        rv_curr_weight.apply {
            setPadding(padding, 0, padding, 0)
            layoutManager = SliderLayoutManager(context, scaleDown = true, needListener = false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("SetTextI18n")
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        tv_weight.text =
                            "${dataWeight[(recyclerView.layoutManager as SliderLayoutManager).findFirstVisibleItemPosition()]} ${getString(
                                R.string.text_default_weight_measure)}"

                }
            })
            adapter = weightSliderAdapter
        }
        weightSliderAdapter?.setData(dataWeight)
    }

    private fun initVars() {
        profileViewModel =
            ViewModelProviders.of(this, viewModelFatory).get(ProfileViewModel::class.java)
    }

    private fun setListeners() {
        btn_create_profile.setOnClickListener {
            if (profileViewModel?.checkDataValidation(
                    tiet_name.text.toString(),
                    auto_complete_textview_sex.text.toString(),
                    tv_height.text.toString(),
                    tv_weight.text.toString())!!
            ) {
                profileViewModel?.manageProfile(
                    EnumCommand.CREATE_PROFILE,
                    tiet_name.text.toString(),
                    auto_complete_textview_sex.text.toString(),
                    tv_height.text.toString(),
                    tv_weight.text.toString())
            }
        }

        tiet_name.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                v.hideKeyboard()
        }

        btn_edit_profile.setOnClickListener {
            if (profileViewModel?.checkDataValidation(
                    tiet_name.text.toString(),
                    auto_complete_textview_sex.text.toString(), tv_height.text.toString(),
                    tv_weight.text.toString()
                )!!
            ) {
                profileViewModel?.manageProfile(
                    EnumCommand.EDIT_PROFILE,
                    tiet_name.text.toString(),
                    auto_complete_textview_sex.text.toString(),
                    tv_height.text.toString(),
                    tv_weight.text.toString()
                )
            }
        }
    }

    private fun observerLiveData() {
        // test
        profileViewModel?.checkIfUserExist()
            ?.observe(this, Observer { value -> accountIfExist(value) })
        profileViewModel?.getProfile()
            ?.observe(this, Observer { profileUI -> populateProfileViews(profileUI) })
        profileViewModel?.isProfileCreatedLiveData?.observe(
            this,
            Observer { isCreated -> isProfileCreated(isCreated) })
        profileViewModel?.isProfileEditedLiveData?.observe(
            this,
            Observer { isEdited -> isProfileEdited(isEdited) })
    }

    private fun populateProfileViews(profileUI: ProfileUI?) {
        profileUI?.let {
            tiet_name.setText(it.fio)
            auto_complete_textview_sex.setText(it.sex)
            tv_height.text = it.currentHeight.toString()
            tv_weight.text = it.currentWeight.toString()
            tv_imt.text = it.imt
            rv_curr_height.smoothScrollToPosition(heightSliderAdapter?.getPosition(it.currentHeight.toInt().toString())!!)
            rv_curr_weight.smoothScrollToPosition(weightSliderAdapter?.getPosition(it.currentWeight.toInt().toString())!!)
        }
    }

    private fun accountIfExist(isExist: Boolean) {
        if (isExist) {
            profileViewModel?.getProfile()
            btn_create_profile.visibility = View.INVISIBLE
            btn_edit_profile.visibility = View.VISIBLE
        } else {
            btn_create_profile.visibility = View.VISIBLE
            btn_edit_profile.visibility = View.INVISIBLE
        }
    }

    private fun isProfileCreated(created: Boolean) {
        if (created) {
            getString(R.string.text_accaunt_create_successfull).showToast(this)
            profileViewModel?.checkIfUserExist()
        } else getString(R.string.text_error_accaunt_create).showToast(this)
    }

    private fun isProfileEdited(isEdited: Boolean) {
        if (isEdited) {
            getString(R.string.text_accaunt_edit_successfull).showToast(this)
            profileViewModel?.getProfile()
        } else getString(R.string.text_error_accaunt_create).showToast(this)
    }

}


