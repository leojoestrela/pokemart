package com.leoeutropio.pokemart.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.leoeutropio.pokemart.model.Pedido
import com.leoeutropio.pokemart.model.Produto

@Dao
interface ProdutoDao {

    @Insert(onConflict = REPLACE)
    fun salvaProduto(produto: Produto)

    @Insert(onConflict = REPLACE)
    fun salvaProduto(produtos: List<Produto>)

    @Insert
    fun salvaPedido(pedido: Pedido)

    @Query("SELECT * FROM Produto")
    fun buscaTodosProdutos(): List<Produto>

    @Query("SELECT * FROM Pedido")
    fun buscaTodosPedidos(): List<Pedido>

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaProdutoPorId(id: String): Produto

    @Query("SELECT * FROM Pedido WHERE id = :id")
    fun buscaPedidoPorId(id: String): Pedido

}