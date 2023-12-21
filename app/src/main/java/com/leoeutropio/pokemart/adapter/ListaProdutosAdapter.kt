package com.leoeutropio.pokemart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leoeutropio.pokemart.R
import com.leoeutropio.pokemart.model.Produto

class ListaProdutosAdapter(listaProdutos: List<Produto> = emptyList()) :
    RecyclerView.Adapter<ListaProdutosAdapter.ProdutoViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private val produtos = listaProdutos.toMutableList()

    interface OnClickListener {
        fun onClick(position: Int, produto: Produto)
        fun onAddCartClick(position: Int, produto: Produto)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.produto_item,
            parent, false
        )
        return ProdutoViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.produtoImagem.setImageResource(produto.image)
        holder.produtoNome.text = produto.nome
        holder.produtoPreco.text = "$ ${produto.preco}"

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, produto)
        }

        holder.adicionarCarrinho.setOnClickListener {
            onClickListener?.onAddCartClick(position, produto)
        }
    }

    class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val produtoImagem: ImageView = itemView.findViewById(R.id.img_item)
        val produtoNome: TextView = itemView.findViewById(R.id.tv_nome_item)
        val produtoPreco: TextView = itemView.findViewById(R.id.tv_preco_item)
        val adicionarCarrinho: Button = itemView.findViewById(R.id.btn_add_cart)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
