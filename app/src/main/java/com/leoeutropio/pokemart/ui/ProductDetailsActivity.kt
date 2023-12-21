package com.leoeutropio.pokemart.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.leoeutropio.pokemart.Constants.Companion.PRODUCT_ID
import com.leoeutropio.pokemart.PrefConfig
import com.leoeutropio.pokemart.database.AppDatabase
import com.leoeutropio.pokemart.databinding.ActivityProductDetailsBinding
import com.leoeutropio.pokemart.model.Produto
import java.util.Timer
import kotlin.concurrent.schedule

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private var quantidade = 1
    private lateinit var listaCarrinho: MutableList<Produto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val produtoID = intent.getStringExtra(PRODUCT_ID)

        binding.apply {
            toolbarActivityProductDetails.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            changeProductQuantity()

            val db = AppDatabase.instancia(baseContext)
            val produtoDao = db.produtoDao()
            val produto = produtoDao.buscaProdutoPorId(produtoID!!)

            configViews(produto)

            buttonAdicionarCarrinho.setOnClickListener {
                produto.quantitade = binding.tvQuantidade.text.toString().toInt()
                var temNoCarrinho = false

                listaCarrinho.forEach { item ->
                    if (item.id == produto.id) {
                        item.quantitade += produto.quantitade
                        temNoCarrinho = true
                    }
                }
                if (!temNoCarrinho) {
                    listaCarrinho.add(produto)
                }

                PrefConfig.writeListInPref(applicationContext, listaCarrinho)

                Toast.makeText(applicationContext, "Produto adicionado", Toast.LENGTH_SHORT).show()
                Timer().schedule(1200) {
                    finish()
                }
            }
        }
    }

    private fun ActivityProductDetailsBinding.configViews(produto: Produto) {
        imgProduto.setImageResource(produto.image)
        tvNomeProduto.text = produto.nome
        tvDescricaoProduto.text = produto.descricao
        tvPrecoProduto.text = "$ ${produto.preco}"
        tvQuantidade.text = quantidade.toString()
    }

    private fun ActivityProductDetailsBinding.changeProductQuantity() {
        imgAdd.setOnClickListener {
            quantidade += 1
            tvQuantidade.text = quantidade.toString()
        }

        imgRemove.setOnClickListener {
            quantidade = if ((quantidade - 1) == 0) 1 else quantidade - 1
            tvQuantidade.text = quantidade.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        listaCarrinho = PrefConfig.readListFromPref(this) as MutableList<Produto>
    }
}