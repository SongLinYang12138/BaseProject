package com.bondex.ysl.battledore.goodslist

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityGoodsListBinding
import com.bondex.ysl.liblibrary.utils.NoDoubleClickListener
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import kotlinx.android.synthetic.main.activity_goods_list.*

class GoodsListActivity : BaseActivity<GoodsListViewModel, ActivityGoodsListBinding>() {
    override fun handleMessage(msg: Int?) {

    }

    override fun initData() {

    }

    var list: MutableList<GoodsListBean> = mutableListOf(GoodsListBean(), GoodsListBean(), GoodsListBean(), GoodsListBean(), GoodsListBean())

    val adapter: GoodslistAdapter = GoodslistAdapter(list)


    override fun initView() {


        showTitle("新增计划")
        showLeft(true, object : NoDoubleClickListener() {
            override fun click(v: View?) {

                finish()
            }
        })

        val manager = LinearLayoutManager(this)
        goodsl_recylerView.layoutManager = manager
        goodsl_recylerView.adapter = adapter
        goodsl_bt_add.setOnClickListener(listener)
        goodsl_bt_cancel.setOnClickListener(listener)

        goodsl_ck_all.setOnCheckedChangeListener { buttonView, isChecked ->

            adapter.checkAll(isChecked)
        }

    }

    override fun onMyClick(view: View?) {

        when (view?.id) {

            R.id.goodsl_bt_cancel -> {

                finish()
            }
            R.id.goodsl_bt_add -> {

                val intent = Intent(GoodsListActivity@this, WorkBetchActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.window_in,R.anim.window_out)
            }


        }

    }

    override fun getReourceId(): Int {

        return R.layout.activity_goods_list
    }

}
