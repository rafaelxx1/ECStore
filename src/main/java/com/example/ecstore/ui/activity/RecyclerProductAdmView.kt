package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ProductAdmListBinding
import com.example.ecstore.ui.recyclerview.adapter.ProductAdmListAdapter

class RecyclerProductAdmView : AppCompatActivity() {
    private val binding by lazy { ProductAdmListBinding.inflate(layoutInflater)}
    private val adapter = ProductAdmListAdapter(this, emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btnVoltar = binding.btnVoltar
        val btnAddProduct = binding.btnAddProduct

        btnAddProduct.setOnClickListener {
            val intent = Intent(this, ProductFormActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, DashboardAdmActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }

        configureRecycler()
    }

    override fun onResume() {
        super.onResume()
        val db = ECStoreDataBase.getInstance(this)
        val productDao = db.ProductDao()

        adapter.update(productDao.findAll())
    }


    private fun configureRecycler() {
        val recyclerView = binding.recyclerProductList
        recyclerView.adapter = adapter
    }
}