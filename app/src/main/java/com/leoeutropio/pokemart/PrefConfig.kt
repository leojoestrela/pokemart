package com.leoeutropio.pokemart

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leoeutropio.pokemart.Constants.Companion.CART_LIST_KEY
import com.leoeutropio.pokemart.model.Produto


class PrefConfig {
    companion object {
        fun writeListInPref(context: Context, lista: List<Produto>) {
            val gson = Gson()
            val jsonString = gson.toJson(lista)

            val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = pref.edit()
            editor.putString(CART_LIST_KEY, jsonString)
            editor.apply()
        }

        fun readListFromPref(context: Context): List<Produto> {
            val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val jsonString = pref.getString(CART_LIST_KEY, "")

            val gson = Gson()
            val type = object : TypeToken<List<Produto?>?>() {}.type

            return gson.fromJson(jsonString, type) ?: mutableListOf()
        }

        fun clearPref(context: Context) {
            val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = pref.edit()
            editor.clear()
            editor.apply()
        }
    }

}