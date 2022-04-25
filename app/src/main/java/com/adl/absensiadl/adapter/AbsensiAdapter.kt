package com.adl.ujiancrud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adl.absensiadl.R
import com.adl.absensiadl.repository.TblAbsenItem


class AbsensiAdapter(val data: ArrayList<TblAbsenItem?>):RecyclerView.Adapter<AbsentViewHolder>() {
    lateinit var parent:ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsentViewHolder {
        this.parent = parent

        return AbsentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_absent,parent,false))
    }

    override fun onBindViewHolder(holder: AbsentViewHolder, position: Int) {
       holder.bindData(this@AbsensiAdapter,position)
    }

    override fun getItemCount(): Int {
      return data.size
    }
}