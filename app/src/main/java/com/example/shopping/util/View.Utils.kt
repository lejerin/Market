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

fun Context.replaceFramgnet(from: Fragment, fragment: Fragment, addToBackStack: Boolean, frame: Int, isAnimRight: Boolean) {
    try {
        //getFragmentManager().popBackStackImmediate();
        val transaction: FragmentTransaction = from.fragmentManager!!.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        //transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        if(isAnimRight){
            transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_right,
                R.anim.enter_from_left,
                R.anim.exit_to_left
            )
        }else{
            transaction.setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_up,
                R.anim.slide_in_down,
                R.anim.slide_out_down
            )
        }

        transaction.replace(frame, fragment)
        transaction.commit()
    } catch (e: Exception) {
    }
}

