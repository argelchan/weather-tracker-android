package com.argel.weathertraker.ui.customs.informativeDialog

import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class FinishedBottomDialogView :
//    BaseBottomSheetDialogFragment(R.layout.finished_bottom_sheet_dialog) {
//    @Inject lateinit var appTheme: AppTheme
//    private lateinit var binding: FinishedBottomSheetDialogBinding
//    private var dismissedCallback: () -> Unit = {}
//    fun setDismissedCallback(dismissedCallback: () -> Unit) {
//        this.dismissedCallback = dismissedCallback
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        isCancelable = false
//    }
//
//    override fun setBinding(view: View) {
//        binding = FinishedBottomSheetDialogBinding.bind(view)
//
//        binding.apply {
//            btnFinish.style(appTheme.getFinishButtonStyle())
//            lifecycleOwner = viewLifecycleOwner
//            btnFinish.setOnClickListener {
//                btnFinish.isEnabled = false
//                dismissedCallback()
//            }
//        }
//    }
//
//}