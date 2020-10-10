package com.example.shopping.util

import com.example.shopping.R
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.shopping.ui.post.PostActivity


fun Context.getActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
}

fun Context.startPostActivity() =
    Intent(this, PostActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.replaceFramgnet(from: Fragment, fragment: Fragment, addToBackStack: Boolean) {
    try {
        //getFragmentManager().popBackStackImmediate();
        val transaction: FragmentTransaction = from.fragmentManager!!.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        //transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        transaction.setCustomAnimations(
            R.anim.slide_in_up,
            R.anim.slide_out_up,
            R.anim.slide_in_down,
            R.anim.slide_out_down
        )
        transaction.replace(R.id.main_post_frame, fragment)
        transaction.commit()
    } catch (e: Exception) {
    }
}