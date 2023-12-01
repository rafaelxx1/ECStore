package com.example.ecstore.ui.recyclerview.adapter

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.ecstore.databinding.ProductItemBinding
import com.example.ecstore.model.Produto
import com.example.ecstore.ui.activity.ProductConfirmationActivity
import java.math.BigDecimal

class ProductListAdapter(
    private val context: Context,
    produto: List<Produto>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val produtos = produto.toMutableList()

    class ViewHolder(
        private val context: Context,
        binding: ProductItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val name = binding.valueProductTitle
        private val price = binding.valueProductPrice
        private val btn = binding.btnProduct
        private val img = binding.imageView


        fun putIt(product: Produto) {

            name.text = Editable.Factory.getInstance().newEditable(product.nome)
            price.text = Editable.Factory.getInstance().newEditable(product.price.toString())
            btn.text = "SELECIONAR"
            img.setImageURI(product.img?.toUri())
            btn.setOnClickListener {
                val intent: Intent = Intent(context, ProductConfirmationActivity::class.java)
                intent.putExtra("product", product.id.toString())
                context.startActivity(intent)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //CRIA O VIEW HOLDER
        //PEGA CADA VIEW CRIADA, E INSERE NO LAYOUT

        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        // QUANTOS ITENS SERAO APRESENTADOS DENTRO DO RECYCLER

        return produtos.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // INDICA O ITEM COLOCADO NO RECYLER, E A POSICAO
        val product = produtos[position]

        holder.putIt(product)



    }

    fun update(prod: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(prod)
        notifyDataSetChanged()
    }

}
