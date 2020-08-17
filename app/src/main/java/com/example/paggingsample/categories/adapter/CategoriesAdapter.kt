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
import com.example.paggingsample.categories.models.UIModel
import kotlinx.android.synthetic.main.lay_list_item.view.*
import kotlinx.android.synthetic.main.separator_item.view.*

class CategoriesAdapter(private val glideDeligate: JlGlideDeligate) :
    PagingDataAdapter<UIModel, RecyclerView.ViewHolder>(diffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.lay_list_item -> CategoriesViewHolder(v, glideDeligate)
            R.layout.header_item -> HeaderViweHolder(v)
            else -> SeperatorViweHolder(v)
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is UIModel.CategoryModel -> R.layout.lay_list_item
        is UIModel.SeparatorModel -> R.layout.separator_item
        is UIModel.Header -> R.layout.header_item
        is UIModel.Footer -> R.layout.activity_main
        null -> throw IllegalStateException("Unknown view")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            val item = getItem(position)
            if (holder is CategoriesViewHolder) {
                holder.onBindView(item as UIModel.CategoryModel)
            } else if (holder is SeperatorViweHolder) {
                holder.onBindView(item as UIModel.SeparatorModel)
            }

        }
    }

    inner class HeaderViweHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun onBindView(data: UIModel.Header) {
            itemView.apply {
//                tv_seperator?.setText(data.description)
            }
        }
    }


    inner class SeperatorViweHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun onBindView(data: UIModel.SeparatorModel) {
            itemView.apply {
                tv_seperator?.setText(data.description)
            }
        }
    }

    inner class CategoriesViewHolder(
        itemView: View, val glideDeligatee: JlGlideDeligate
    ) : RecyclerView.ViewHolder(itemView) {

        fun onBindView(data: UIModel.CategoryModel) {
            itemView.apply {
                data.item.icons?.let {
                    if (it.isNotEmpty() &&
                        !it[0].url.isNullOrEmpty())
                        glideDeligatee?.load(im_categories,it[0].url!!)
                }

                tv_category?.text = data.item.name
            }
        }
    }

    companion object {

        object diffCallback : DiffUtil.ItemCallback<UIModel>() {
            override fun areItemsTheSame(
                oldItem: UIModel,
                newItem: UIModel
            ): Boolean {
                val isSameRepoItem = oldItem is UIModel.CategoryModel
                        && newItem is UIModel.CategoryModel
                        && oldItem.item.id == newItem.item.id

                val isSameSeparatorItem = oldItem is UIModel.SeparatorModel
                        && newItem is UIModel.SeparatorModel
                        && oldItem.description == newItem.description

                return isSameRepoItem || isSameSeparatorItem
            }

            override fun areContentsTheSame(
                oldItem: UIModel,
                newItem: UIModel
            ) = oldItem == newItem
        }
    }

}