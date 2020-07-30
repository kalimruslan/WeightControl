package ru.ruslan.weighttracker.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_sheet_add_weight.*
import kotlinx.android.synthetic.main.dialog_bottom_sheet_add_weight.view.*
import ru.ruslan.weighttracker.R
import java.lang.ClassCastException
import javax.inject.Inject

class AddWeightBottomSheetDialog @Inject constructor() : BottomSheetDialogFragment() {
    interface AddWeightBottomSheetListener {
        fun cancelButtonClicked()
        fun okButtonClicked(weightValue: String)
    }

    private lateinit var listener: AddWeightBottomSheetListener

    fun setOnAddWeightBottomSheetListener(listener: AddWeightBottomSheetListener){
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_bottom_sheet_add_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.buttonSubmit.setOnClickListener {
            if(view.etWeight.text.toString().isNotEmpty()){
                listener.okButtonClicked(view.etWeight.text.toString())
                dismiss()
            }
        }
        view.buttonCancel.setOnClickListener {
            listener.cancelButtonClicked()
            dismiss()
        }
    }
}