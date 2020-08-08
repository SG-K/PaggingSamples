package com.example.paggingsample

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class JlGlideDeligate constructor(context: Context) {

    private var context: Context? = null

    init {
        this.context = context.applicationContext
    }

    fun load(imageView: ImageView, imageUrl: String) {
        context?.let {
            Glide.with(it)
                .load(imageUrl)
                .into(imageView)
        }
    }


    fun load(imageView: ImageView, imageUrl: Int) {
        context?.let {
            Glide.with(it)
                .load(imageUrl)
                .into(imageView)
        }
    }



    fun loadWithPlaceHolder(imageView: ImageView, imageUrl: String) {
        context?.let {
            Glide.with(it)
                .load(imageUrl)
//                .placeholder(R.drawable.jl_ic_glide_placeholder)
                .into(imageView)
        }
    }

    fun loadWithPlaceHolderCropCenter(imageView: ImageView, imageUrl: String) {
        context?.let {

            val myOptions = RequestOptions()
                .centerCrop()

            Glide.with(it)
                .load(imageUrl)
//                .placeholder(R.drawable.jl_ic_glide_placeholder)
                .apply(myOptions)
                .into(imageView)
        }
    }

    fun loadWithPlaceHolderImage(imageView: ImageView, imageUrl: String) {
        context?.let {
            Glide.with(it)
                .load(imageUrl)
//                .placeholder(R.drawable.jl_ic_default_image)
                .into(imageView)
        }
    }

    fun loadWithCenterCrop(imageView: ImageView, imageUrl: String) {
        context?.let {
            val myOptions = RequestOptions()
                .centerCrop()
            Glide.with(it)
                .load(imageUrl)
                .apply(myOptions)
                .into(imageView)
        }
    }

    fun loadWithCenterCropCircler(imageView: ImageView, imageUrl: String) {
        context?.let {
            val myOptions = RequestOptions()
                .centerCrop()
                .optionalCircleCrop()
            Glide.with(it)
                .load(imageUrl)
                .apply(myOptions)
                .into(imageView)
        }
    }

    fun loadWithCropCircler(imageView: ImageView, imageUrl: String) {
        context?.let {
            val myOptions = RequestOptions()
                .circleCrop()

            Glide.with(it)
                .load(imageUrl)
//                .placeholder(R.drawable.jl_ic_circler_drawable)
                .apply(myOptions)
                .into(imageView)
        }
    }

    fun loadWithCropCircler(imageView: ImageView, imageUrl: Int) {
        context?.let {
            val myOptions = RequestOptions()
                .circleCrop()

            Glide.with(it)
                .load(imageUrl)
//                .placeholder(R.drawable.jl_ic_circler_drawable)
                .apply(myOptions)
                .into(imageView)
        }
    }

}