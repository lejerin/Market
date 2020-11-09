package com.example.shopping.ui.search

import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.databinding.RcRowItemHomeBinding
import com.example.shopping.databinding.RcRowItemSearchBinding
import com.example.shopping.util.MyApplication

class SearchAdapter (
    private val productList : List<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object
    {
        private const val VIEW_TYPE_DATA = 0;
        private const val VIEW_TYPE_PROGRESS = 1;
    }

    private var isNext : String? = null
    fun setIsNext(str: String?){
        isNext = str
    }

    override fun getItemCount() = if (isNext != null) productList.size+1 else productList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType)
    {
        VIEW_TYPE_DATA ->
        {//inflates row layout
            SearchViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.rc_row_item_search,
                    parent,
                    false
                )
            )
        }
        VIEW_TYPE_PROGRESS ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress,parent,false)
            ProgressViewHolder(view)
        }
        else -> throw IllegalArgumentException("Different View type")
    }

    override fun getItemViewType(position: Int): Int
    {
        if(position < productList.size) {
            return VIEW_TYPE_DATA
        }

        return VIEW_TYPE_PROGRESS
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is SearchViewHolder)
        {
            holder.searchBinding.product = productList[position]

        }

    }

    inner class SearchViewHolder(
        val searchBinding: RcRowItemSearchBinding
    ) : RecyclerView.ViewHolder(searchBinding.root)

    inner class ProgressViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView)
}