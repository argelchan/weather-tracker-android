package com.argel.weathertraker.ui.customs.informativeDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.argel.weathertraker.R
import com.argel.weathertraker.databinding.InformativeDialogBinding

class InformativeDialog(
    private val title: Int,
    private val description: Int,
    private val negativeText: Int = R.string.textCancel,
    private val positiveText: Int = R.string.textAccept,
    private val negativeEnabled: Boolean = true,
    private val negativeCallback: () -> Unit = {},
    private val positiveCallback: () -> Unit
    ) : DialogFragment() {

    private lateinit var binding: InformativeDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.informative_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding(view)
    }

    private fun setBinding(view: View) {
        binding = InformativeDialogBinding.bind(view)

        binding.apply {
            txvTitle.text = getString(title)
            txvDescription.text = getString(description)
            btnAccept.text = getString(positiveText)
            btnCancel.text = getString(negativeText)
            btnCancel.isEnabled = negativeEnabled
            btnCancel.setOnClickListener {
                dismiss()
                negativeCallback()
            }
            btnAccept.setOnClickListener {
                dismiss()
                positiveCallback()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}