package com.bondex.ysl.battledore.workbench

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseActivity
import com.bondex.ysl.battledore.databinding.ActivityWorkBetchBinding
import com.bondex.ysl.battledore.util.Constant
import com.bondex.ysl.liblibrary.utils.*
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_work_betch.*

class WorkBetchActivity : BaseActivity<WorkBetchViewModle, ActivityWorkBetchBinding>() {

    val list = mutableListOf<String>()
    val adapter = WorkBetchAdapter(list)

    val scanIntent = IntentIntegrator(WorkBetchActivity@ this)
    var mainCode: String? = null

    override fun initView() {

        showLeft(true, object : NoDoubleClickListener() {
            override fun click(v: View?) {
                finish()
            }

        })
        showTitle("打板工作台")

        FormatTextUtils.formatText(work_main_code, FormatTextUtils.AIR_PRIMARY_NUM)

        work_main_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                if (s?.length == 12) {

                    val unit = Tools.getAirPrimaryCheckCode(s.toString())
                    mainCode = s.toString() + unit

                    work_main_verify.setText(unit)
                    mainCode = mainCode?.replace(" ", "")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        work_battle_date.setOnClickListener(listener)
        work_pickup_date.setOnClickListener(listener)
        work_it_main_code.setOnClickListener(listener)
        work_it_hawb.setOnClickListener(listener)


        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")

        adapter.updateList(list)

        val manager = LinearLayoutManager(this)
        work_recycleview.layoutManager = manager
        work_recycleview.adapter = adapter
    }

    override fun onMyClick(view: View?) {

        when (view?.id) {

            R.id.work_it_main_code -> {

                scanIntent.setRequestCode(Constant.SCAN_MAIN_REQUEST_CODE).initiateScan()
            }
            R.id.work_it_hawb -> {
                scanIntent.setRequestCode(Constant.SCAN_HAWB_REQUEST_CODE).initiateScan()

            }
            R.id.work_battle_date -> {

                DateUtils.showDate(WorkBetchActivity@ this, { it ->
                    work_battle_date.setText(it)

                })

            }

            R.id.work_pickup_date -> {


                DateUtils.showDate(WorkBetchActivity@ this,  {it ->

                    work_pickup_date.setText(it)
                })

            }

        }

    }

    override fun getReourceId(): Int {

        return R.layout.activity_work_betch
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                Constant.SCAN_MAIN_REQUEST_CODE -> {

                    val code = data?.getStringExtra(Intents.Scan.RESULT)
                    work_main_code.setText(code)

                }
                Constant.SCAN_HAWB_REQUEST_CODE -> {

                    val code = data?.getStringExtra(Intents.Scan.RESULT)
                    work_hawb.setText(code)

                }


            }


        }


    }
}


