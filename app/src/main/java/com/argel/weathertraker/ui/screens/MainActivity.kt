package com.argel.weathertraker.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.argel.weathertraker.R
import com.argel.weathertraker.core.presentation.BaseActivity
import com.argel.weathertraker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        baseNavController.setGraph(R.navigation.main_graph)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.WHITE, Color.WHITE)
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun layoutId() = R.layout.activity_main

    override val fragmentContainer: FragmentContainerView
        get() = binding.fragmentContainerMain

    override fun setUpNavigation(navController: NavController) {
        NavigationUI.setupWithNavController(binding.mainBottomNav, navController)
        NavigationUI.setupWithNavController(
            binding.toolbar, navController, AppBarConfiguration(binding.mainBottomNav.menu)
        )
    }

    override fun showProgress(show: Boolean) {
        binding.progressView.progressLayout.isVisible = show
    }

    override fun setBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId())
        binding.apply {
            lifecycleOwner = this@MainActivity
            binding.mainBottomNav.apply {
                setOnItemReselectedListener {}
            }
        }
    }

    override fun enableBottomNav(enable: Boolean) {
        binding.mainBottomNav.isVisible = enable
    }

    override fun enableAppBar(enable: Boolean) {
        binding.appbar.isVisible = enable
    }

    override fun setToolbarContent(content: ToolbarContent) {
        binding.toolbar.apply {

            title = content.title
            subtitle = content.subtitle
            navigationIcon = content.navigationIcon?.let {
                setNavigationOnClickListener {
                    content.onClickNavigationIcon()
                }
                AppCompatResources.getDrawable(context, it)
            }

            try {
                (getChildAt(0) as TextView).setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    content.titleSize.toFloat()
                )

                for (i in 1..childCount) try {
                    (getChildAt(i) as TextView).setTextSize(
                        TypedValue.COMPLEX_UNIT_SP,
                        content.subtitleSize.toFloat()
                    )
                } catch (_: Exception) {
                }
            } catch (_: Exception) {
            }

            content.menu?.let {
                menu.clear()
                inflateMenu(it)

                menu[0].actionView?.setOnClickListener {
                    content.onMenuItemClicked()
                } ?: menu[0].setOnMenuItemClickListener {
                    content.onMenuItemClicked()
                    true
                }

            } ?: menu.clear()
        }
    }

    override fun setBottomView(view: View) {
        binding.flBottomView.removeAllViews()
        binding.flBottomView.addView(view)
    }

    override fun enableBottomView(enable: Boolean) {
        binding.flBottomView.isVisible = enable
    }

}