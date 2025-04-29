package com.argel.weathertraker.core.presentation

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.argel.weathertraker.R
import com.argel.weathertraker.core.exception.Failure
import com.soriana.sorianadelivery.core.presentation.OnFailure
import kotlinx.coroutines.DelicateCoroutinesApi

@Suppress("DEPRECATION")
@DelicateCoroutinesApi
abstract class BaseActivity : AppCompatActivity(), OnFailure {

    abstract fun layoutId(): Int
    abstract val fragmentContainer: FragmentContainerView
    val baseNavController by lazy { (supportFragmentManager
        .findFragmentById(R.id.fragmentContainerMain ) as NavHostFragment)
        .navController }

    //private var dialog: InformativeDialog? = null

    internal fun hideKeyBoard() {
        val view = this.findViewById<View>(android.R.id.content)
        if (view != null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    internal fun showKeyBoard() {
        val view = this.findViewById<View>(android.R.id.content)
        if (view != null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInputFromWindow(view.windowToken, 0, 0)
        }
    }

    abstract fun setUpNavigation(navController: NavController)

    override fun handleFailure(failure: Failure?) {
        showProgress(false)
    }

    abstract fun showProgress(show: Boolean)
    abstract fun setBinding()
    abstract fun enableBottomNav(enable: Boolean)
    abstract fun enableAppBar(enable: Boolean)

    data class ToolbarContent(
        val title: String = "",
        val titleSize: Int = 20,
        val subtitle: String = "",
        val subtitleSize: Int = 16,
        val menu: Int? = null,
        val navigationIcon: Int? = null,
        val onClickNavigationIcon: () -> Unit = {},
        val onMenuItemClicked: () -> Unit = {}
    )

    abstract fun setToolbarContent(content: ToolbarContent)
    abstract fun setBottomView(view: View)
    abstract fun enableBottomView(enable: Boolean)

    //internal fun showDialogToUpdateApp() {
    //    dialog?.let {
    //        if (it.showsDialog)
    //            it.dismiss()
    //    }
    //    dialog = InformativeDialog(
    //        principalTitle = R.string.alertUpdate,
    //        title = R.string.alertUpdateDescription,
    //        negativeEnabled = false,
    //        positiveText = R.string.text_update,
    //        negativeText = R.string.text_update,
    //        positiveCallback = {
    //            val intent = Intent(Intent.ACTION_VIEW)
    //            intent.data = Uri.parse("market://details?id=$packageName")
    //            startActivity(intent)
    //        }
    //    )
    //    dialog?.isCancelable = false
    //    dialog?.show(supportFragmentManager, "")
    //}

}