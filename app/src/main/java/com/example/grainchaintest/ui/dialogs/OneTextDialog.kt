package com.example.grainchaintest.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.grainchaintest.R
import kotlinx.android.synthetic.main.dialog_one_text.view.*

class OneTextDialog : DialogFragment() {

    private lateinit var title: String
    private lateinit var rootView : View

    companion object {

        @JvmStatic
        fun newInstance(title : String) : OneTextDialog{

            val dialog = OneTextDialog()

            dialog.title = title

            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.dialog_one_text, container, false)

        rootView.dialogOneTextTitleTextView.text = title

        this.initListeners()

        return rootView
    }

    private fun initListeners() {

        rootView.dialogOneTextAcceptButton.setOnClickListener {
            this.dismiss()
        }
    }
}