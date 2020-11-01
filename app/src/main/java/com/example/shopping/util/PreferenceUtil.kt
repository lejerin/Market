package com.example.shopping.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)


    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getHomeItemWidth(): Int{
        return prefs.getInt("HomeItemWidth", 0)
    }

    fun setHomeItemWidth(value: Int){
        prefs.edit().putInt("HomeItemWidth", value).apply()
    }
}

