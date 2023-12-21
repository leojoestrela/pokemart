package com.leoeutropio.pokemart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokemart.R
import com.leoeutropio.pokemart.model.Pedido
import com.leoeutropio.pokemart.model.Produto

class ListaPedidosAdapter(listaPedidos: List<Pedido> = emptyList()) :
    RecyclerView.Adapter<ListaPedidosAdapter.PedidoViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private val pedidos = listaPedidos.toMutableList()

    interface OnClickListener {
        fun onClick(position: Int, pedido: Pedido)
    }

    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pedidoNumero: TextView = itemView.findViewById(R.id.tv_numero_pedido)
        val pedidoValorTotal: TextView = itemView.findViewById(R.id.tv_valor_total_pedido)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.pedido_item,
            parent, false
        )
        return PedidoViewHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.pedidoNumero.text = "Id do pedido: ${pedido.id}"
        holder.pedidoValorTotal.text = "Valor total: $${valorTotal(pedido)}"

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, pedido)
        }

    }

    private fun valorTotal(pedido: Pedido): Int {
        var valor = 0
        pedido.produtos.forEach { produto ->
            valor += produto.quantitade * produto.preco.toInt()
        }

        return valor
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    override fun getItemCount(): Int {
        return pedidos.size
    }

    fun atualiza(pedidos: List<Pedido>) {
        this.pedidos.clear()
        this.pedidos.addAll(pedidos)
        notifyDataSetChanged()
    }

}