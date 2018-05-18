package example.aleks.com.herokuapp.presentation.mvvm

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


/**
 * Created by Aleksandar on 2.5.2018 г..
 */

@BindingAdapter("app:update")
fun setUpdate(recyclerView: RecyclerView, update: Boolean) {

    if (recyclerView.adapter != null) {
        recyclerView.adapter.notifyDataSetChanged()
    }
}

@BindingAdapter("app:imageUrl")
fun setImage(imageView: ImageView, url: String) {

    Glide.with(imageView)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
}

