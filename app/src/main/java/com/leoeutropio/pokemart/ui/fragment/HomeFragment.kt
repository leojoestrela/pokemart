package com.leoeutropio.pokemart.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.leoeutropio.pokemart.Constants.Companion.PRODUCT_ID
import com.leoeutropio.pokemart.PrefConfig
import com.leoeutropio.pokemart.adapter.ListaProdutosAdapter
import com.leoeutropio.pokemart.database.AppDatabase
import com.leoeutropio.pokemart.databinding.FragmentHomeBinding
import com.leoeutropio.pokemart.model.Produto
import com.leoeutropio.pokemart.ui.ProductDetailsActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = ListaProdutosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerListaProdutos.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerListaProdutos.layoutManager = layoutManager

        val listaCarrinho: MutableList<Produto> =
            PrefConfig.readListFromPref(requireContext()) as MutableList<Produto>

        adapter.setOnClickListener(object : ListaProdutosAdapter.OnClickListener {
            override fun onClick(position: Int, produto: Produto) {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra(PRODUCT_ID, produto.id.toString())
                startActivity(intent)
            }

            override fun onAddCartClick(position: Int, produto: Produto) {
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
                PrefConfig.writeListInPref(requireContext(), listaCarrinho)

                Toast.makeText(context, "Produto adicionado", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(requireContext())
        val produtoDao = db.produtoDao()
        adapter.atualiza(produtoDao.buscaTodosProdutos())
    }

}