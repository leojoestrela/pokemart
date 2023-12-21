package com.leoeutropio.pokemart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokemart.R
import com.leoeutropio.pokemart.model.Produto

class ListaCarrinhoAdapter(listaProdutosCarrinho: List<Produto> = emptyList()) :
    RecyclerView.Adapter<ListaCarrinhoAdapter.ItemCarrinhoViewHolder>() {

    private val produtosCarrinho = listaProdutosCarrinho.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemCarrinhoViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.carrinho_item,
            parent, false
        )
        return ItemCarrinhoViewHolder(viewLayout)
    }


    override fun getItemCount(): Int {
        return produtosCarrinho.size
    }

    override fun onBindViewHolder(holder: ItemCarrinhoViewHolder, position: Int) {
        val produto = produtosCarrinho[position]
        holder.itemCarrinhoImagem.setImageResource(produto.image)
        holder.itemCarrinhoNome.text = produto.nome

        val valorTotal = produto.preco.toInt() * produto.quantitade
        holder.itemCarrinhoValorTotal.text = "Valor total: $$valorTotal"
        holder.itemCarrinhoQuantindade.text = "Quantitade: x${produto.quantitade}"
        holder.itemCarrinhoValorUnitario.text = "Valor unitario: $${produto.preco}"
    }

    class ItemCarrinhoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemCarrinhoImagem: ImageView = itemView.findViewById(R.id.img_item_carrinho)
        val itemCarrinhoNome: TextView = itemView.findViewById(R.id.tv_nome_item_carrinho)
        val itemCarrinhoValorTotal: TextView =
            itemView.findViewById(R.id.tv_valor_total_item_carrinho)
        val itemCarrinhoValorUnitario: TextView =
            itemView.findViewById(R.id.tv_valor_unitario_item_carrinho)

        val itemCarrinhoQuantindade: TextView =
            itemView.findViewById(R.id.tv_quantidade_item_carrinho)
    }

    fun valorTotal(): Int {
        var valor = 0
        produtosCarrinho.forEach { produto ->
            valor += produto.quantitade * produto.preco.toInt()
        }
        return valor
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtosCarrinho.clear()
        this.produtosCarrinho.addAll(produtos)
        notifyDataSetChanged()
    }

}