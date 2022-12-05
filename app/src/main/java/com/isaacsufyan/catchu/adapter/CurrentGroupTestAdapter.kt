package com.isaacsufyan.catchu.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isaacsufyan.catchu.databinding.ListItemGroupTestTableBinding
import com.isaacsufyan.catchu.domain.enum.EStatusType
import com.isaacsufyan.catchu.domain.model.TestHistoryModel

class CurrentGroupTestAdapter : RecyclerView.Adapter<CurrentGroupTestAdapter.ViewHolder>() {

    private var list: List<TestHistoryModel>? = null

    fun setList(list: List<TestHistoryModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemGroupTestTableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val testHistoryModel = list?.get(position)
        holder.binding.model = testHistoryModel

        if (testHistoryModel?.status == EStatusType.Pending.name){
            holder.binding.tvStatus.setTextColor(Color.RED)
        }else{
            holder.binding.tvStatus.setTextColor(Color.BLUE)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class ViewHolder(var binding: ListItemGroupTestTableBinding) :
        RecyclerView.ViewHolder(binding.root)
}