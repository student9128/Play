package com.kevin.play.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.kevin.play.R
import com.kevin.play.adapter.BannerAdapter
import com.kevin.play.base.BaseFragment
import com.kevin.play.bean.HomeBannerData
import com.kevin.play.contract.HomeContract
import com.kevin.play.data.RequestDataSource
import com.kevin.play.presenter.HomePresenter
import com.kevin.play.util.ViewPagerUtils
import com.kevin.play.view.transformer.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Kevin on 2018/12/5<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
class NavFragment : BaseFragment(), HomeContract.View {

    var homeBanner: ViewPager? = null
    private var currentPosition = 1

    override fun showError(data: List<Any>?) {
        verifyState(data)
    }

    private var d: MutableList<HomeBannerData> = ArrayList()

    private var adapter: BannerAdapter? = null
    private var data = ArrayList<HomeBannerData>()
    override fun addDisposable(d: Disposable) {
        cDisposable.add(d)
    }

    private var mPresenter: HomeContract.Presenter? = null
    override fun showTips(msg: String) {
        toast(msg)
    }


    override fun setPresenter(presenter: HomeContract.Presenter) {
        mPresenter = presenter
    }

    companion object {
        open fun newInstance(args: String): NavFragment {
            var fragment = NavFragment()
            var bundle = Bundle()
            bundle.putString("args", args)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setLayoutResId(): Int {
        return R.layout.fragment_nav
    }

    override fun initView() {


//        homeBanner.setPageTransformer(true, PagerTransformer3D())
//        homeBanner.setPageTransformer(true, PagerTransformerAlpha())
//        homeBanner.setPageTransformer(true, PagerTransformerCover())
//        homeBanner.setPageTransformer(true, PagerTransformerSmooth())
//        homeBanner.setPageTransformer(true, PagerTransformer3DInner())

    }


    override fun initListener() {
    }

    override fun loadData() {
    }

    override fun showDataBanner(data: List<HomeBannerData>) {

    }


}