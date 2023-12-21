package com.leoeutropio.pokemart.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Pedido(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var produtos: List<Produto>
)