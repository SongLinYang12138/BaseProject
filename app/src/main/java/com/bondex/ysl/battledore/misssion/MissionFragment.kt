package com.bondex.ysl.battledore.misssion


import android.content.Intent
import android.text.InputType
import android.util.Log
import android.view.View
import com.bondex.ysl.battledore.R
import com.bondex.ysl.battledore.base.BaseFragment
import com.bondex.ysl.battledore.databinding.FragmentMissionBinding
import com.bondex.ysl.battledore.ui.MenuList
import com.bondex.ysl.battledore.workbench.WorkBetchActivity
import com.bondex.ysl.camera.ISCameraConfig
import com.bondex.ysl.camera.ISNav
import com.bondex.ysl.camera.MainHawbBean
import kotlinx.android.synthetic.main.fragment_mission.*
import java.util.ArrayList


class MissionFragment : BaseFragment<MissionViewModel, FragmentMissionBinding>() {
    override fun showLoading() {

    }

    override fun stopLoading() {
    }

    override fun handleMessage(msg: Int?) {

    }

    var adapter: MissionAdapterJ? = null
    val REQUEST_CAMERA: Int = 1121
    private val menuList = mutableListOf<String>("航班日期", "航班号", "件数", "日期")

    override fun getResourceId(): Int {

        return R.layout.fragment_mission
    }


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


        mission_search.edit.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        mission_search.edit.setHint("主单号\\分单号\\航班号\\目的港")


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
        val mainHawbs: ArrayList<MainHawbBean> = arrayListOf(
            MainHawbBean("756-45442145", "HWS73289656"),
            MainHawbBean("756-454756445", "HWS789656"),
            MainHawbBean("756-454554865", "HWS785656"),
            MainHawbBean("756-454512445","HWS789456")
        )

        val config: ISCameraConfig = ISCameraConfig.Builder()
            .setHawbBeans(mainHawbs).build()

        mission_it_camera.setOnClickListener {

            ISNav.getInstance().toCamera(MissionFragment@ this, config, REQUEST_CAMERA)
        }

        mission_menulist.addData(menuList)
        mission_menulist.setOnMenuClick(object : MenuList.MenuClick {
            override fun menuChoice(position: Int, menu: String) {

            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val list: ArrayList<CharSequence>? = data?.getCharSequenceArrayListExtra("result")
        val list1: ArrayList<CharSequence>? = data?.getCharSequenceArrayListExtra("camera_key")
        Log.i("aaa", "onActivity result" + "  " + list1?.get(0) + " 2 " + list1?.size)
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
