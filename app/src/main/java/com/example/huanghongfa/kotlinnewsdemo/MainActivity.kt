package com.example.huanghongfa.kotlinnewsdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.huanghongfa.kotlinnewsdemo.adapter.NewsListAdapter
import com.example.huanghongfa.kotlinnewsdemo.entity.ChannelEntity
import com.example.huanghongfa.kotlinnewsdemo.entity.DeviceInfoEntity
import com.example.huanghongfa.kotlinnewsdemo.entity.NewsEntity
import com.example.huanghongfa.kotlinnewsdemo.entity.User
import com.example.huanghongfa.kotlinnewsdemo.service.RequestCommon
import com.example.huanghongfa.kotlinnewsdemo.service.response.ChannelDataResponse
import com.example.huanghongfa.kotlinnewsdemo.service.response.NewsDataResponse
import com.example.huanghongfa.kotlinnewsdemo.service.response.UserResponse
import com.example.huanghongfa.kotlinnewsdemo.utils.DeviceInfo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * 主界面
 */
class MainActivity : AppCompatActivity() {

    val mContext = this;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsList.layoutManager = LinearLayoutManager(mContext)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    /**
     * 这里的doAsync需要使用到anko的一个lib
     */
    private fun initData() = doAsync {
        val userID = getUserId()
        uiThread {
            getChannel(userID)
        }
    }

    /**
     * 获取用户ID，用于获取新闻信息的唯一标识
     */
    fun getUserId(): User {
        val gson: Gson = Gson()
        val params: DeviceInfoEntity = DeviceInfoEntity(DeviceInfo.getDeviceIDByAndroidID(mContext));
        val url = RequestCommon.INIT_URL + gson.toJson(params)
        val jsonStr = RequestCommon(url).run()
        val user :User = gson.fromJson(jsonStr, UserResponse::class.java).data;
        Log.d("MainActivity", " user " + user.user_id);
        return user;
    }


    /**
     * 根据用户ID获取到新闻的渠道
     */
    private fun getChannel(user: User) = doAsync {
        val channelList = getChannelList(user.user_id)
        Log.d("MainActivity", " channelList " + channelList.toString());
        uiThread {
            loadNewsDataList(user.user_id, channelList[0].id!!)
        }
    }


    fun getChannelList(uid: String): ArrayList<ChannelEntity> {
        val gson: Gson = Gson()
        val url = RequestCommon.GET_CHANNEL + "&timestamp=" + System.currentTimeMillis() + "&uid=" + uid
        val jsonStr = RequestCommon(url).run()
        val channelList: ArrayList<ChannelEntity> = gson.fromJson(jsonStr, ChannelDataResponse::class.java).data.channel_list;
        return channelList;
    }


    /**
     * 加载新闻列表数据
     */
    private fun loadNewsDataList(uid: String, cid: String) = doAsync {
        val list = getNewsList(uid, cid)
        Log.d("MainActivity", "loadNewsDataList list " + list.toString());
        uiThread {
            newsList.adapter = NewsListAdapter(list) {
                toast(it.title)
            }
        }
    }

    /**
     * 获取新闻列表
     */
    fun getNewsList(uid: String, cid: String): ArrayList<NewsEntity> {
        val gson: Gson = Gson()
        val url = RequestCommon.GET_NEWS_LIST + "&uid=" + uid + "&type_id=" + cid
        val jsonStr = RequestCommon(url).run()
        val newsList: ArrayList<NewsEntity> = gson.fromJson(jsonStr, NewsDataResponse::class.java).data
        return newsList;
    }

    private fun toast(title: String?) {

        Toast.makeText(mContext, title, Toast.LENGTH_SHORT).show()
    }
}
