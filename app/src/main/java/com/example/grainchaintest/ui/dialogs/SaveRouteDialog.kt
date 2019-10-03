package com.example.grainchaintest.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.grainchaintest.R
import com.example.grainchaintest.interfaces.SaveRouteInterface
import kotlinx.android.synthetic.main.dialog_save_user.view.*

class SaveRouteDialog : DialogFragment() {

    private lateinit var saveListener : SaveRouteInterface
    private lateinit var rootView : View

    companion object {

        @JvmStatic
        fun newInstance(saveListener : SaveRouteInterface) : SaveRouteDialog {

            val dialog = SaveRouteDialog()

            dialog.saveListener = saveListener

            return dialog
        }
    }

    override fun onStart() {
        super.onStart()

        val wm : WindowManager = activity!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val win = dialog!!.window
        if (win != null) {
            win.setBackgroundDrawableResource(android.R.color.white)
            win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            val width = (metrics.widthPixels * 0.9)
            val height = (metrics.heightPixels * 0.4)
            win.setLayout(width.toInt(), height.toInt())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.dialog_save_user, container, false)

        this.initListeners()

        return rootView
    }

    private fun initListeners() {

        rootView.dialogSaveCancelButton.setOnClickListener {
            this.dismiss()
        }

        rootView.dialogSaveSaveButton.setOnClickListener {
            saveListener.onSaveRoute(rootView.dialogSaveUserEditText.text.toString())
            this.dismiss()
        }
    }
}