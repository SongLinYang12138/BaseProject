package com.bondex.ysl.battledore.battledoredetail

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityBattleDoreDetailBinding
import com.bondex.ysl.battledore.photo.PhotoActivity
import com.bondex.ysl.battledore.ui.TextItemDecoration
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.databaselibrary.plan.PlanBean
import kotlinx.android.synthetic.main.activity_battle_dore_detail.*

class BattleDoreDetailActivity : BaseActivity<BattleDoreDetailViewModel, ActivityBattleDoreDetailBinding>() {

    var planBean: PlanBean? = null

    override fun getReourceId(): Int {

        return R.layout.activity_battle_dore_detail
    }

    override fun initView() {

        showTitle("打板详情")
        showLeft(true) {
            finish()
        }

        showRight(true, R.string.img) {

            val intent = Intent(this@BattleDoreDetailActivity, PhotoActivity::class.java)
            intent.putParcelableArrayListExtra(Constant.HAWB_BEAN_KEY, planBean?.hawbs)
            startActivity(intent)

        }


        work_plate_type.adapter = viewModel.getPlateTypeAdapter()
        val hawbManager = LinearLayoutManager(this@BattleDoreDetailActivity)
        work_recycleview.layoutManager = hawbManager
        work_recycleview.adapter = viewModel.hawbAdapter
        work_recycleview.addItemDecoration(TextItemDecoration())

        val protectManager = LinearLayoutManager(this)
        protectManager.orientation = LinearLayoutManager.HORIZONTAL

        work_protect_type_recyclerView.layoutManager = protectManager
        work_protect_type_recyclerView.adapter = viewModel?.protectAdapter

        val subplateManager = LinearLayoutManager(this)
        subplateManager.orientation = LinearLayoutManager.HORIZONTAL
        work_subplate_recyclerView.layoutManager = subplateManager
        work_subplate_recyclerView.adapter = viewModel.subplateAdapter


        if (planBean == null) return

        dore_detail_flight.text = planBean?.flight
        dore_detail_detination.text = planBean?.destination
        dore_detail_flight_date.text = planBean?.flghtDate
        work_plate_num.text = planBean?.boardNum
        work_plate_lock.text = planBean?.lockNum
        work_plate_type.prompt = planBean?.plateType?.name
        dore_detail_date.text = planBean?.battleDate



        planBean?.hawbs?.let { viewModel.hawbAdapter.updateList(it) }
        planBean?.protectType?.let { viewModel.protectAdapter.updateList(it) }
        planBean?.subPlateTypel?.let { viewModel.subplateAdapter.updateList(it) }

    }


    override fun onMyClick(view: View?) {
    }

    override fun handleMessage(msg: Int?) {

    }

    override fun initData() {

        planBean = intent.getParcelableExtra(Constant.PLAN_BEAN_KEY)

    }

}
