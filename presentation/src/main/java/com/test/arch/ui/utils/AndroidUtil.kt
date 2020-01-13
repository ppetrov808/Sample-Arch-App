package com.test.arch.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import timber.log.Timber

object AndroidUtil {

    fun openLink(context: Context?, link: String?) {
        openLink(context, link, "")
    }

    fun openLink(context: Context?, link: String?, mime: String) {
        if (link.isNullOrEmpty()) return
        val i = Intent()
        i.action = Intent.ACTION_VIEW
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            i.data = Uri.parse(link)
            if (!TextUtils.isEmpty(mime)) {
                i.type = mime
            }
            context?.startActivity(i)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}