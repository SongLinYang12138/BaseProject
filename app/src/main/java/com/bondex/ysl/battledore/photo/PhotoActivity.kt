package com.bondex.ysl.battledore.photo

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityPhotoBinding
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : BaseActivity<PhotoViewModel, ActivityPhotoBinding>() {


    override fun getReourceId(): Int {
        return R.layout.activity_photo
    }

    override fun initData() {

        val list: ArrayList<HAWBBean> = intent.getParcelableArrayListExtra(Constant.HAWB_BEAN_KEY)

        if (list != null) {

            viewModel.getHwabImg(list)
        }
    }

    override fun initView() {

        showLeft(true) {
            finish()
        }
        showTitle("分单照片")
        showRight(true, R.string.delete) {

            viewModel.delete()
        }

        val manager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        photo_recyclerview.layoutManager = manager
        photo_recyclerview.adapter = viewModel.adapter
    }

    override fun handleMessage(msg: Int?) {
    }

    override fun onMyClick(view: View?) {
    }

    override fun startLoading() {
        super.startLoading()
    }

    override fun stopLoading() {
        super.stopLoading()
    }

}
