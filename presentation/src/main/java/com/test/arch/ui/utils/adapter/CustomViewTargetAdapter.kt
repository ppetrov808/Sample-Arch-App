package com.test.arch.ui.utils.adapter

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition

open class CustomViewTargetAdapter<T : View, Z>(view: T) : CustomViewTarget<T, Z>(view) {

    override fun onLoadFailed(errorDrawable: Drawable?) {}

    override fun onResourceCleared(placeholder: Drawable?) {}

    override fun onResourceReady(resource: Z, transition: Transition<in Z>?) {}
}