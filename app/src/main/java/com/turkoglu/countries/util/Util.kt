package com.turkoglu.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.core.content.contentValuesOf
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.turkoglu.countries.R
import kotlin.coroutines.coroutineContext

//Extension
/*
fun String.myExtension(myparameter: String) {
    println(myparameter)
}
 */
fun ImageView.downloadFromUrl(url:String?,progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}
fun placeholderProgresBar(context : Context) : CircularProgressDrawable{
    return  CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}
@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView,url:String?){
    view.downloadFromUrl(url, placeholderProgresBar(view.context))
}
