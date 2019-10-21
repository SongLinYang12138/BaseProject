package com.bondex.ysl.battledore.plan

import android.util.Log
import com.bondex.ysl.battledore.base.BaseViewModle
import com.bondex.ysl.databaselibrary.plan.WorkBentchChoiceBean
import com.bondex.ysl.camera.ui.utils.SHA
import com.bondex.ysl.databaselibrary.hawb.HAWBBean
import com.bondex.ysl.databaselibrary.plan.BattleBoardBean
import com.bondex.ysl.databaselibrary.plan.PlanBean
import com.bondex.ysl.databaselibrary.plan.PlanBeanDao
import com.bondex.ysl.liblibrary.utils.Tools
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */

class PlanViewModel : BaseViewModle() {

    val planBeans = arrayListOf<PlanBean>()
    val adapter: PlanAdapter = PlanAdapter(planBeans)

    override fun onCreate() {


        getData(1)

        insertTestData()
    }

    fun refresh() {

        getData(1)
    }

    fun getData(page: Int) {


        val observable = Observable.create(object : ObservableOnSubscribe<List<PlanBean>> {
            override fun subscribe(emitter: ObservableEmitter<List<PlanBean>>) {

                val dao = PlanBeanDao(context)

                val offset = page * 20
                val list: List<PlanBean> = dao.getPlans(20, offset)

                Log.i("aaa", "page " + page + "  size " + list.size)
                emitter.onNext(list)

            }
        })
        val consumer = Consumer<List<PlanBean>> {

            if (it.size > 0) {

                planBeans.addAll(it)
                adapter.updataList(planBeans)
                setRefresh(true)
            }
        }


        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer)
    }


    private fun insertTestData() {


        val assetManager = context.resources.assets


        val thread = Thread(object : Runnable {
            override fun run() {
                val inp = assetManager.open("battledore_data.json")

                val bos = ByteArrayOutputStream()

                var len: Int = 0

                while (len != -1) {
                    len = inp.read()
                    bos.write(len)
                }

                val dao = PlanBeanDao.getInstance(context)
                val json = String(bos.toByteArray())

                val gson = Gson()
                val jsonArray = JSONArray(json)

                val list: List<PlanBean> = dao.getPlans(20, 0)

                Log.i("aaa", "lismitsize " + list.size)
                if (list.size > 0) return

                for (i in 0..jsonArray.length() - 1) {

                    val planBean = PlanBean()
                    planBean.id = i.toString()

                    val jsonObject = jsonArray.getJSONObject(i)
                    val boardBean = gson.fromJson(jsonObject.toString(), BattleBoardBean::class.java)
                    planBean.toPlanBean(boardBean)

                    dao.intsert(planBean)
                }

                Log.i("aaa", " listsize " + planBeans.size)


                bos.flush()
                inp.close()
                bos.close()


            }
        })

        thread.start()

    }

    fun toAZ(num: Int): String {

        val letter = (num % 26 + 'A'.toInt()).toChar().toString()
        return letter
    }

    //数字转字母 1-26 ： A-Z
    private fun numberToLetter(num: Int): String? {
        var num = num
        if (num <= 0) {
            return null
        }
        var letter = ""
        num--
        do {
            if (letter.length > 0) {
                num--
            }
            num = (num - num % 26) / 26
        } while (num > 0)

        return letter

    }


    override fun onResume() {
    }

    override fun onStart() {

    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    override fun registerRxBus() {
    }

    override fun removeRxBus() {
    }


}

