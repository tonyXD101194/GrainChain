package com.example.grainchaintest.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grainchaintest.R
import com.example.grainchaintest.interfaces.ActionsRouteInterface
import com.example.grainchaintest.models.DetailModel
import com.example.grainchaintest.viewHolders.RouteViewHolder

class RouteAdapter(private val list : List<DetailModel>,
                   private val context : Context,
                   private val callback : ActionsRouteInterface) : RecyclerView.Adapter<RouteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_route, parent, false)

        return RouteViewHolder(view,context,callback)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(list[position])
        holder.setIsRecyclable(false)
    }

    fun removeItem(model: DetailModel) {

        (list as ArrayList).remove(model)

        this.notifyDataSetChanged()
    }
}