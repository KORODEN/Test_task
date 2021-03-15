package com.koroden.test_task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val listData: List<DataModel>, val clickListener: ClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        var newsDate: TextView
        var newsHeader: TextView

        init {
            newsDate = view.findViewById(R.id.newsDate)
            newsHeader = view.findViewById(R.id.newsHeader)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(listData[position].header == ""){
            if(listData[position].text.length <= 20) {
                listData[position].header = listData[position].text
                holder.newsHeader.text = listData[position].header
            }
            else{
                val helpHeader : String = listData[position].text.substring(0, 20).substringBeforeLast(' ')

                if(helpHeader[helpHeader.length - 1] == '…'){
                    listData[position].header = helpHeader
                    holder.newsHeader.text = listData[position].header
                }
                else{
                    listData[position].header = "$helpHeader…"
                    holder.newsHeader.text = listData[position].header
                }
            }
        }else
            holder.newsHeader.text = listData[position].header

        holder.newsDate.text = listData[position].date


        holder.itemView.setOnClickListener {
            clickListener.onItemClick(listData.get(position))
        }
    }

    interface ClickListener{
        fun onItemClick(dataModel: DataModel){

        }
    }
}