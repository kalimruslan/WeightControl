package ru.ruslan.weighttracker.ui.profile

import android.annotation.SuppressLint
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile_current_height.*
import kotlinx.android.synthetic.main.content_profile_current_weight.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.ProfileScope
import ru.ruslan.weighttracker.domain.contract.ProfileContract
import ru.ruslan.weighttracker.ui.BaseActivity
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.util.*
import ru.ruslan.weighttracker.ui.util.Constants.THIS_APP
import javax.inject.Inject

@ProfileScope
class ProfileActivity : BaseActivity(R.layout.activity_profile), ProfileContract.View {

    @Inject lateinit var presenter: ProfileContract.Presenter

    private lateinit var heightSliderAdapter: SliderAdapter
    private lateinit var weightSliderAdapter: SliderAdapter
    private lateinit var dataHeight: List<String>
    private lateinit var dataWeight: List<String>

    override fun initDagger() {
        THIS_APP.getAppComponent().getProfileComponent().create().inject(this)
    }

    override fun initMembers() {
        presenter.setView(this)

        dataHeight = (140..210).toList().map { it.toString() }
        dataWeight = (40..150).toList().map { it.toString() }

        heightSliderAdapter = SliderAdapter(object : SliderAdapter.OnItemCLickListener {
            @SuppressLint("SetTextI18n")
            override fun clickItem(pos: Int) {
                tv_height.text =
                    "${dataHeight[pos]} + ${getString(R.string.text_default_height_measuer)}"
                rv_curr_height.smoothScrollToPosition(pos)
            }
        })

        weightSliderAdapter = SliderAdapter(object : SliderAdapter.OnItemCLickListener {
            @SuppressLint("SetTextI18n")
            override fun clickItem(pos: Int) {
                tv_weight.text =
                    "${dataWeight[pos]} ${getString(R.string.text_default_weight_measure)}"
                rv_curr_weight.smoothScrollToPosition(pos)
            }
        })
    }

    override fun initViews() {
        profile_toolbar.setNavigationIcon(R.drawable.ic_close)
        profile_toolbar.setNavigationOnClickListener {
            if (presenter.accountIfExist())
                startActivityExt<MainActivity>(this)
            onBackPressed()
        }
        profile_toolbar.title = resources.getString(R.string.profile)

        auto_complete_textview_sex.setAdapter(
            ArrayAdapter(
                this, R.layout.item_sex_exposed_dropdown_popup,
                resources.getStringArray(R.array.sex_dropdown)
            )
        )

        val padding: Int = ScreenUtil.getScreenWidth(this) / 2 - ScreenUtil.dpToPx(this, 40)

        rv_curr_height.apply {
            setPadding(padding, 0, padding, 0)
            layoutManager = SliderLayoutManager(context, scaleDown = true, needListener = false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("SetTextI18n")
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        tv_height.text =
                            "${dataHeight[(recyclerView.layoutManager as SliderLayoutManager).findFirstVisibleItemPosition()]} ${getString(
                                R.string.text_default_height_measuer
                            )}"
                }
            })
            adapter = heightSliderAdapter
        }
        heightSliderAdapter.setData(dataHeight)

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
                                R.string.text_default_weight_measure
                            )}"

                }
            })
            adapter = weightSliderAdapter
        }
        weightSliderAdapter.setData(dataWeight)
    }

    override fun setListeners() {

        tiet_name.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                v.hideKeyboard()
        }

        btn_create_profile.setOnClickListener {
            presenter.buttonCreateProfileClicked(
                tiet_name.text.toString(),
                auto_complete_textview_sex.text.toString(),
                tv_height.text.toString(),
                tv_weight.text.toString()
            )
        }

        btn_edit_profile.setOnClickListener {
            presenter.buttonEditProfileClicked(
                tiet_name.text.toString(),
                auto_complete_textview_sex.text.toString(), tv_height.text.toString(),
                tv_weight.text.toString()
            )
        }
    }

    override fun hasItAccount(isHas: Boolean) {
        if (isHas) {
            presenter.getCurrentProfile()
            btn_create_profile.visibility = View.INVISIBLE
            btn_edit_profile.visibility = View.VISIBLE
        } else {
            btn_create_profile.visibility = View.VISIBLE
            btn_edit_profile.visibility = View.INVISIBLE
        }
    }

    override fun populateProfileViews(profileUI: ProfileUI?) {
        profileUI?.let {
            tiet_name.setText(it.fio)
            auto_complete_textview_sex.setText(it.sex)
            tv_height.text = it.currentHeight.toString()
            tv_weight.text = it.currentWeight.toString()
            tv_imt.text = it.imt
            rv_curr_height.smoothScrollToPosition(
                heightSliderAdapter.getPosition(
                    it.currentHeight.toInt().toString()
                )!!
            )
            rv_curr_weight.smoothScrollToPosition(
                weightSliderAdapter.getPosition(
                    it.currentWeight.toInt().toString()
                )!!
            )
        }
    }

    override fun showToastProfileCreatedSuccess() =
        getString(R.string.text_accaunt_create_successfull).showToast(this)

    override fun showToastProfileCreatedError() =
        getString(R.string.text_error_accaunt_create).showToast(this)

    override fun showToastProfileEditedSuccess() =
        getString(R.string.text_accaunt_edit_successfull).showToast(this)

    override fun showToastProfileEditedError() =
        getString(R.string.text_error_accaunt_create).showToast(this)
}