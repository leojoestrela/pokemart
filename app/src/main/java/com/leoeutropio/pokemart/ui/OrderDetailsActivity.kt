package com.leoeutropio.pokemart.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.leoeutropio.pokemart.Constants.Companion.ORDER_ID
import com.leoeutropio.pokemart.adapter.ListaCarrinhoAdapter
import com.leoeutropio.pokemart.database.AppDatabase
import com.leoeutropio.pokemart.databinding.ActivityOrderDetailsBinding

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private val adapter = ListaCarrinhoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pedidoId = intent.getStringExtra(ORDER_ID)

        binding.apply {
            toolbarActivityOrderDetails.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            recyclerOrderDetails.adapter = adapter
            val layoutManager = LinearLayoutManager(this@OrderDetailsActivity)
            recyclerOrderDetails.layoutManager = layoutManager

            val db = AppDatabase.instancia(baseContext)
            val produtoDao = db.produtoDao()
            val pedido = produtoDao.buscaPedidoPorId(pedidoId!!)

            adapter.atualiza(pedido.produtos)

            binding.tvValorTotal.text = "Valor Total: $${adapter.valorTotal()}"
        }
    }
}