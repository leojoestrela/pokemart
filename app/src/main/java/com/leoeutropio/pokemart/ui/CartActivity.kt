package com.leoeutropio.pokemart.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.leoeutropio.pokemart.PrefConfig
import com.leoeutropio.pokemart.adapter.ListaCarrinhoAdapter
import com.leoeutropio.pokemart.database.AppDatabase
import com.leoeutropio.pokemart.databinding.ActivityCartBinding
import com.leoeutropio.pokemart.model.Pedido
import com.leoeutropio.pokemart.model.Produto
import java.util.Timer
import kotlin.concurrent.schedule

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val adapter = ListaCarrinhoAdapter()

    private lateinit var listaCarrinho: MutableList<Produto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaCarrinho = PrefConfig.readListFromPref(this) as MutableList<Produto>

        binding.apply {
            toolbarActivityCart.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            recyclerCarrinho.adapter = adapter
            val layoutManager = LinearLayoutManager(this@CartActivity)
            recyclerCarrinho.layoutManager = layoutManager

            adapter.atualiza(listaCarrinho)

            if (adapter.itemCount > 0) {
                clResumoPedido.visibility = View.VISIBLE
                tvCarrinhoVazio.visibility = View.GONE
            } else {
                clResumoPedido.visibility = View.GONE
                tvCarrinhoVazio.visibility = View.VISIBLE
            }

            binding.tvValor.text = "$${adapter.valorTotal()}"

            buttonFinalizar.setOnClickListener {
                creatOrder()
            }
        }

    }

    private fun creatOrder() {
        val pedido = Pedido(produtos = listaCarrinho)

        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()
        produtoDao.salvaPedido(pedido)

        Toast.makeText(this@CartActivity, "Pedido finalizado", Toast.LENGTH_SHORT).show()
        Timer().schedule(1200) {
            PrefConfig.clearPref(this@CartActivity)
            finish()
        }
    }

}