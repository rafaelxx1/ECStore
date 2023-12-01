package com.example.ecstore.ui.recyclerview.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.ecstore.databinding.ProductAdmItemBinding
import com.example.ecstore.databinding.ProductItemBinding
import com.example.ecstore.model.Produto
import com.example.ecstore.ui.activity.ProductConfirmationActivity
import com.example.ecstore.ui.activity.ProductEditActivity

class ProductAdmListAdapter(
    private val context: Context,
    product: List<Produto>
) : RecyclerView.Adapter<ProductAdmListAdapter.ViewHolder>() {
    private val produtos = product.toMutableList()

    class ViewHolder(
        private val context: Context,
        binding: ProductAdmItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val name = binding.txtName
        private val price = binding.txtPrice
        private val btn = binding.btnEditProduct
        private val img = binding.productItemImg


        fun putIt(product: Produto) {

            name.text = product.nome
            price.text = product.price.toString()
            btn.text = "editar"
            img.setImageURI(product.img?.toUri())
            btn.setOnClickListener {
                val intent = Intent(context, ProductEditActivity::class.java)
                intent.putExtra("ID_PRODUCT", product.id.toString())
                intent.putExtra("IMG_PATH", product.img)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductAdmItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = produtos[position]
        holder.putIt(product)
    }

    fun update(prod: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(prod)
        notifyDataSetChanged()
    }
}