package com.leoeutropio.pokemart.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Produto(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var nome: String,
    var descricao: String,
    var quantitade: Int,
    var preco: String,
    var image: Int
) : Serializable