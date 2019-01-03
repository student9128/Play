package com.kevin.play

import android.animation.ArgbEvaluator
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.health.PackageHealthStats
import android.provider.Settings
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.TextView
import com.kevin.play.adapter.TabFragmentAdapter
import com.kevin.play.base.BaseActivity
import com.kevin.play.base.BaseDialog
import com.kevin.play.constant.Constants
import com.kevin.play.manager.PermissionManager
import com.kevin.play.ui.fragment.HomeFragment
import com.kevin.play.ui.fragment.NavFragment
import com.kevin.play.ui.fragment.PersonFragment
import com.kevin.play.ui.fragment.ProjectFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : BaseActivity(), TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    var tabIcon = intArrayOf(R.drawable.ic_home, R.drawable.ic_nav, R.drawable.ic_project, R.drawable.ic_person)
    var tabIconFilled = intArrayOf(R.drawable.ic_home_filled, R.drawable.ic_nav_filled, R.drawable.ic_project_filled, R.drawable.ic_person_filled)
    private var tabList = intArrayOf(R.string.home, R.string.nav, R.string.project, R.string.person)
    private var fragmentList: ArrayList<Fragment>? = ArrayList()
    private var adapter: TabFragmentAdapter? = null
    private var homeFragment: HomeFragment? = null
    private var projectFragment: ProjectFragment? = null
    override fun setLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        initFragmentList()
        adapter = TabFragmentAdapter(this, tabList, tabIcon, tabIconFilled, fragmentList, supportFragmentManager)
        viewPager.adapter = adapter
        with(viewPager) {
            currentItem = 0
            offscreenPageLimit = 1
        }
        with(tabLayout) {
            setupWithViewPager(viewPager)
            tabMode = TabLayout.MODE_FIXED
        }
        for (i in 0..tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.customView = adapter!!.getTabView(i)
        }
        setToolbarTitle(0)
        homeFragment = HomeFragment()
        projectFragment = ProjectFragment()

        checkPermission()
    }

    private fun checkPermission() {
        if (!PermissionManager.hasPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            PermissionManager.requestPermission(this, Constants.WRITE_EXTERNAL_STORAGE, R.string.permission_EXTERNAL_STORAGE_rationale, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    PermissionManager.requestPermission(this, Constants.WRITE_EXTERNAL_STORAGE, R.string.permission_EXTERNAL_STORAGE_rationale, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    printD("执行了")
                    showDialog(-1, R.string.permission_EXTERNAL_STORAGE_rationale, object : BaseDialog.DialogButtonClickListener {
                        override fun onLeftBtnClick(dialog: DialogInterface) {
                        }

                        override fun onRightBtnClick(dialog: DialogInterface) {
                            startAppSettings()
                        }

                    })
                }
            }
        }

    }

//    private fun startAppSettings() {
//        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.data = Uri.parse("package:$packageName")
//        startActivity(intent)
//    }

    override fun initListener() {
        tabLayout.addOnTabSelectedListener(this)
        viewPager.addOnPageChangeListener(this)
    }


    private fun initFragmentList() {
        fragmentList?.clear()
        fragmentList?.let {
            it.add(homeFragment ?: HomeFragment())
            it.add(NavFragment.newInstance("Nav"))
            it.add(projectFragment ?: ProjectFragment())
            it.add(PersonFragment.newInstance("Person"))
        }

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        setTabSelected(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        setTabUnselected(tab)
    }


    override fun onTabSelected(tab: TabLayout.Tab?) {
        setTabSelected(tab)
    }


    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val selectedColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val unSelectedColor = ContextCompat.getColor(this, R.color.gray)
        var colorEvaluator = ArgbEvaluator()
        val eSelected: Int = colorEvaluator.evaluate(positionOffset, unSelectedColor, selectedColor) as Int
        val eUnselected: Int = colorEvaluator.evaluate(positionOffset, selectedColor, unSelectedColor) as Int
        if (positionOffset > 0) {
            val tab0 = tabLayout.getTabAt(position)
            val tab1 = tabLayout.getTabAt(position + 1)
            val tabView0 = tab0!!.customView
            val tabView1 = tab1!!.customView
            tab0?.let {
                it.customView!!.findViewById<TextView>(R.id.tabTitle).setTextColor(eUnselected)
//                it.customView!!.findViewById<ImageView>(R.id.tabIcon).setColorFilter(eUnselected)
                it.customView!!.findViewById<ImageView>(R.id.tabIcon).imageAlpha = (255 * positionOffset).toInt()
                it.customView!!.findViewById<ImageView>(R.id.tabIconFilled).imageAlpha = (255 * (1 - positionOffset)).toInt()
            }
            tab1.let {
                it.customView!!.findViewById<TextView>(R.id.tabTitle).setTextColor(eSelected)
//                it.customView!!.findViewById<ImageView>(R.id.tabIcon).setColorFilter(eSelected)
                it.customView!!.findViewById<ImageView>(R.id.tabIcon).imageAlpha = (255 * (1 - positionOffset)).toInt()
                it.customView!!.findViewById<ImageView>(R.id.tabIconFilled).imageAlpha = (255 * positionOffset).toInt()


            }

//            val tabTitle0 = tabView0!!.findViewById<TextView>(R.id.tabTitle)
//            val tabTitle1 = tabView1!!.findViewById<TextView>(R.id.tabTitle)
//            tabTitle0.setTextColor(eUnselected)
//            tabTitle1.setTextColor(eSelected)
        }
    }

    override fun onPageSelected(position: Int) {
        viewPager.setCurrentItem(position, false)
        setToolbarTitle(position)


    }

    private fun setToolbarTitle(position: Int) {
        supportActionBar!!.title = when (position) {
            0 -> getString(R.string.home)
            1 -> getString(R.string.nav)
            2 -> getString(R.string.project)
            3 -> getString(R.string.person)
            else -> {
                getString(R.string.app_name)
            }
        }
    }

    private fun setTabSelected(tab: TabLayout.Tab?) {
        var view = tab!!.customView
        var tabTitle = view!!.findViewById<TextView>(R.id.tabTitle)
        var tabIcon = view!!.findViewById<ImageView>(R.id.tabIcon)
        var tabIconFilled = view!!.findViewById<ImageView>(R.id.tabIconFilled)
        //        tabIcon.imageTintList = ColorStateList.valueOf(R.color.colorPrimary)
        tabTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//        tabIcon.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        tabIconFilled.imageAlpha = 255
        tabIcon.imageAlpha = 0
        setToolbarTitle(tab.position)
    }


    private fun setTabUnselected(tab: TabLayout.Tab?) {
        var view = tab!!.customView
        var tabTitle = view!!.findViewById<TextView>(R.id.tabTitle)
        var tabIcon = view!!.findViewById<ImageView>(R.id.tabIcon)
        var tabIconFilled = view!!.findViewById<ImageView>(R.id.tabIconFilled)
        //        tabIcon.imageTintList = ColorStateList.valueOf(R.color.colorPrimary)
        tabTitle.setTextColor(ContextCompat.getColor(this, R.color.gray))
//        tabIcon.setColorFilter(ContextCompat.getColor(this, R.color.gray), PorterDuff.Mode.SRC_IN)
        tabIconFilled.imageAlpha = 0
        tabIcon.imageAlpha = 255
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onPause() {
        super.onPause()
        printI("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        printI("onDestroy")
    }
}
