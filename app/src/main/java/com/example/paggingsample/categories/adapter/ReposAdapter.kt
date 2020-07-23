package com.example.paggingsample.categories.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.paggingsample.R
import com.example.paggingsample.categories.models.ItemsItem
import com.example.paggingsample.categories.print
import kotlinx.android.synthetic.main.lay_list_item.view.*

//class ReposAdapter :
//    RecyclerView.Adapter<ReposAdapter.modelViewHolder>() {
class ReposAdapter : PagingDataAdapter<ItemsItem, ReposAdapter.modelViewHolder>(diffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): modelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.lay_list_item, parent, false)
        return modelViewHolder(v, viewType)
    }


//    override fun onBindViewHolder(holder: modelViewHolder, position: Int) {
//        arrayList?.let {
//            holder.onBindView(it[position], position)
//        }
//    }

//    fun setData(users: List<ItemsItem>?) {
//        users?.let {
//            arrayList?.clear()
//            arrayList?.addAll(it)
//            "size in adapter ${arrayList?.size}".print()
//            notifyDataSetChanged()
//        }
//    }


    inner class modelViewHolder(
        itemView: View,
        viewType: Int
    ) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
            }
        }

        fun onBindView(data: ItemsItem, position: Int) {
            itemView.apply {
                tv_category?.text = data.name
            }
        }
    }

    override fun onBindViewHolder(holder: modelViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBindView(it,position)
        }
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean =
                oldItem == newItem
        }
    }

}