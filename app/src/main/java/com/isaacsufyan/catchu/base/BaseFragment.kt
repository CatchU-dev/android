package com.isaacsufyan.catchu.base

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.domain.model.RawMeasurements
import com.isaacsufyan.catchu.framework.interfaces.IDialogOnClickListener
import com.isaacsufyan.catchu.presentation.activities.main.MainActivity
import com.isaacsufyan.catchu.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    protected lateinit var calendar: Calendar
    lateinit var rawMeasurements: RawMeasurements

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = requireActivity() as MainActivity
        rawMeasurements = RawMeasurements()
        calendar = Calendar.getInstance()
    }

    fun showOkDialog(message: String) {
        mainActivity.showDialog(
            true,
            message,
            R.drawable.correct_info_icon,
            null,
            resources.getString(R.string.okay),
            object : IDialogOnClickListener {
                override fun onOk(dialog: AlertDialog) {
                    dialog.dismiss()
                }
            })
    }

    fun showOkDialog(title: String, listener: IDialogOnClickListener) {
        mainActivity.showDialog(
            true,
            message = title,
            R.drawable.practice_icon,
            null,
            resources.getString(R.string.okay),
            listener
        )
    }

    fun showDialogAndNavigateScreen(message: String, action: NavDirections) {
        mainActivity.showDialog(
            false,
            message,
            R.drawable.correct_info_icon,
            null,
            null,
            object : IDialogOnClickListener {
                override fun onOk(dialog: AlertDialog) {
                    dialog.dismiss()
                    findNavController().navigate(action)
                }

                override fun onCancel(dialog: AlertDialog) {
                    dialog.dismiss()
                }
            })
    }


}