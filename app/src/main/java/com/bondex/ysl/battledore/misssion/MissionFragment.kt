package com.bondex.ysl.battledore.misssion


import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentMissionBinding
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import kotlinx.android.synthetic.main.fragment_mission.*


class MissionFragment : BaseFragment<MissionViewModel, FragmentMissionBinding>() {

    var adapter: MissionAdapterJ? = null

    override fun initView() {

        val list = mutableListOf<MissionBean>()

        val bean = MissionBean()

        bean.mainCode = "miancode1235"

        val childList = mutableListOf<String>()

        childList.add("1")
        childList.add("1")
        childList.add("1")
        childList.add("1")
        bean.hawbs = childList


        val childList1 = mutableListOf<String>()

        childList1.add("2")
        childList1.add("2")
        childList1.add("2")
        childList1.add("2")
        val bean1 = MissionBean()
        bean1.mainCode = "miancode1235"
        bean1.hawbs = childList1


        val childList2 = mutableListOf<String>()

        childList2.add("3")
        childList2.add("3")
        childList2.add("3")
        val bean2 = MissionBean()
        bean2.mainCode = "miancode1235"
        bean2.hawbs = childList2


        val childList3 = mutableListOf<String>()

        childList3.add("4")
        childList3.add("4")
        val bean3 = MissionBean()
        bean3.mainCode = "miancode1235"
        bean3.hawbs = childList3




        list.add(bean)
        list.add(bean1)
        list.add(bean2)
        list.add(bean3)

        mission_workbetch.setOnClickListener(listener)

        item_mission_ck_all.setOnCheckedChangeListener { buttonView, isChecked ->


            adapter?.setSelectAll(isChecked)
        }
        adapter = MissionAdapterJ(list)
//        mission_expand_view.layoutManager = LinearLayoutManager(context)
        mission_expand_view.setAdapter(adapter)

    }

    override fun getResourceId(): Int {

        return R.layout.fragment_mission
    }

    override fun myClick(view: View?) {


        when (view?.id) {

            R.id.mission_workbetch -> {

                val intent = Intent(activity, WorkBetchActivity::class.java)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.window_in, R.anim.window_out)

            }


        }

    }


}
