package com.example.shopping.ui.home

import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R
import com.example.shopping.data.model.ProductResponse
import com.example.shopping.databinding.RcRowItemHomeBinding
import com.example.shopping.util.MyApplication

class HomeAdapter (
    private val productList : List<ProductResponse>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    override fun getItemCount() = productList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rc_row_item_home,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {


        var width = MyApplication.prefs.getHomeItemWidth()
        if(width == 0){
            val displayMetrics = DisplayMetrics()
            (holder.itemView.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

            val deviceWidth = displayMetrics.widthPixels - (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,18.0f,displayMetrics))
            width = (deviceWidth/3).toInt()
            MyApplication.prefs.setHomeItemWidth(width)
        }
        holder.itemView.layoutParams.width = width
        holder.itemView.requestLayout()

        holder.rcBinding.product = productList[position]


    }

    inner class HomeViewHolder(
        val rcBinding: RcRowItemHomeBinding
    ) : RecyclerView.ViewHolder(rcBinding.root)


}