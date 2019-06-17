package com.bondex.ysl.battledore.battledoredetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityBattleDoreDetailBinding
import com.bondex.ysl.battledore.util.NoDoubleClickListener

class BattleDoreDetailActivity : BaseActivity<BattleDoreDetailViewModel, ActivityBattleDoreDetailBinding>() {


    override fun initView() {

        showTitle("打板详情")
        showLeft(true, { v ->
            finish()
        })


    }

    override fun onMyClick(view: View?) {
    }

    override fun getReourceId(): Int {

        return R.layout.activity_battle_dore_detail
    }

}
