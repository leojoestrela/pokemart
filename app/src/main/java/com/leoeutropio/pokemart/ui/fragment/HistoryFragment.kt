package com.leoeutropio.pokemart.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.leoeutropio.pokemart.Constants.Companion.ORDER_ID
import com.leoeutropio.pokemart.adapter.ListaPedidosAdapter
import com.leoeutropio.pokemart.database.AppDatabase
import com.leoeutropio.pokemart.databinding.FragmentHistoryBinding
import com.leoeutropio.pokemart.model.Pedido
import com.leoeutropio.pokemart.ui.OrderDetailsActivity

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val adapter = ListaPedidosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerListaPedidos.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerListaPedidos.layoutManager = layoutManager

        adapter.setOnClickListener(object : ListaPedidosAdapter.OnClickListener {
            override fun onClick(position: Int, pedido: Pedido) {
                val intent = Intent(context, OrderDetailsActivity::class.java)
                intent.putExtra(ORDER_ID, pedido.id)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(requireContext())
        val produtoDao = db.produtoDao()
        adapter.atualiza(produtoDao.buscaTodosPedidos())
    }

}