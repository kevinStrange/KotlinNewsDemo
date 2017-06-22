package com.example.huanghongfa.kotlinnewsdemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.huanghongfa.kotlinnewsdemo.R
import com.example.huanghongfa.kotlinnewsdemo.entity.NewsEntity
import kotlinx.android.synthetic.main.news_item.view.*


/**
 * Created by huanghongfa on 2017/6/21.
 * 新闻列表的适配器
 */
class NewsListAdapter(val list: ArrayList<NewsEntity>, val itemClickListener: (NewsEntity) -> Unit) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {



    /**
     * 获取列表的size
     */
    override fun getItemCount(): Int = list.size


    /**
     * 绑定界面
     */
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(list[position])
    }

    /**
     * 创建需要展示列表的view
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }


    class ViewHolder(view: View, val itemClickListener: (NewsEntity) -> Unit): RecyclerView.ViewHolder(view) {
        fun bind(news: NewsEntity) {
            with(news) {
                //标题
                itemView.title.text = news.title
                //内容
                itemView.desc.text = news.summary
                //图片
                Glide.with(itemView.context).load(news.image).into(itemView.image)
                itemView.setOnClickListener { itemClickListener(this) }
            }
        }
    }
}