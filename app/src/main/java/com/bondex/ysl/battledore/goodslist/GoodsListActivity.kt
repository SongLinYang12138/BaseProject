package com.bondex.ysl.battledore.goodslist

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityGoodsListBinding
import com.bondex.ysl.battledore.util.NoDoubleClickListener
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import kotlinx.android.synthetic.main.activity_goods_list.*

class GoodsListActivity : BaseActivity<GoodsListViewModel, ActivityGoodsListBinding>() {

    var list: MutableList<String> = mutableListOf("12", "45", "45", "1", "2")

    val adapter: GoodslistAdapter = GoodslistAdapter(list)


    override fun initView() {


        showTitle("货物清单")
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


    }

    override fun onMyClick(view: View?) {

        when (view?.id) {

            R.id.goodsl_bt_cancel -> {

                finish()
            }
            R.id.goodsl_bt_add -> {

                val intent = Intent(GoodsListActivity@ this, WorkBetchActivity::class.java)
                startActivity(intent)
            }


        }

    }

    override fun getReourceId(): Int {

        return R.layout.activity_goods_list
    }

}
