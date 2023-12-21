package com.leoeutropio.pokemart.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leoeutropio.pokemart.model.Produto

class Converters {

    @TypeConverter
    fun produtoParaJson(produtos: List<Produto>): String {
        val gson = Gson()
        return gson.toJson(produtos)
    }

    @TypeConverter
    fun jsonParaProduto(produtos: String): List<Produto> {
        val gson = Gson()
        val type = object : TypeToken<List<Produto?>?>() {}.type

        return gson.fromJson(produtos, type) ?: mutableListOf()
    }

}