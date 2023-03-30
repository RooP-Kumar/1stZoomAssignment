package com.example.assignment.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.entity.Repo

class RecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener,
    private val shareClickListener: OnShareClickListener
): RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>() {

    private val dataList: ArrayList<Repo> = arrayListOf()

    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val repoName : TextView = itemView.findViewById(R.id.itemtv)
        val repoDesc : TextView = itemView.findViewById(R.id.itemDesc)
        val shareBtn : ImageView = itemView.findViewById(R.id.shareRepo)

        init {
            itemView.setOnClickListener {
                itemClickListener.onClick(dataList[adapterPosition])
            }

            shareBtn.setOnClickListener{
                shareClickListener.onClick(dataList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.repoName.text = dataList[position].name
        holder.repoDesc.text = dataList[position].description
    }

    fun addData(repos: List<Repo>){
        dataList.clear()
        dataList.addAll(repos)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(repo: Repo)
    }

    interface OnShareClickListener {
        fun onClick(repo: Repo)
    }


}