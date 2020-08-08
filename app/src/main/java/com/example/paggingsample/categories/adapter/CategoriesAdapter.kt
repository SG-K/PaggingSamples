package com.example.paggingsample.categories.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paggingsample.JlGlideDeligate
import com.example.paggingsample.R
import com.example.paggingsample.categories.models.CategoryItem
import kotlinx.android.synthetic.main.lay_list_item.view.*

class CategoriesAdapter(private val glideDeligate: JlGlideDeligate) :
    PagingDataAdapter<CategoryItem, CategoriesAdapter.CategoriesViewHolder>(diffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.lay_list_item, parent, false)
        return CategoriesViewHolder(v,glideDeligate)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBindView(it,position)
        }
    }


    inner class CategoriesViewHolder(
        itemView: View, val glideDeligatee: JlGlideDeligate
    ) : RecyclerView.ViewHolder(itemView) {

        fun onBindView(data: CategoryItem, position: Int) {
            itemView.apply {
                data.icons?.let {
                    if (it.isNotEmpty() &&
                        !it[0].url.isNullOrEmpty())
                        glideDeligatee.load(im_categories,it[0].url!!)
                }

                tv_category?.text = data.name
            }
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<CategoryItem>() {
            override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean =
                oldItem == newItem
        }
    }

}