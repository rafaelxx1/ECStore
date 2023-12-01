package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ProductListBinding
import com.example.ecstore.model.Global
import com.example.ecstore.model.ProductType
import com.example.ecstore.ui.recyclerview.adapter.ProductListAdapter
import java.math.BigDecimal

class RecyclerProductView : AppCompatActivity() {


    private val adapter = ProductListAdapter(this, emptyList())
    private val binding by lazy { ProductListBinding.inflate(layoutInflater) }
    private val global = Global.globalVar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRecycler()
        setContentView(binding.root)

        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()
        val user = userDao.findById(global.toLong())
        val btnBack = binding.txtLogout

        if(user != null){
            binding.titleUser.text = user.nome
        }else {
            binding.titleUser.text = "null"
        }

        btnBack.setOnClickListener{
            val intent = Intent(this, ProductSelectionActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }


    }

    override fun onResume() {
        super.onResume()
        val recived = intent.getStringExtra("type_product")
        val db = ECStoreDataBase.getInstance(this)
        val productDao = db.ProductDao()
        val products = productDao.findAll().filter {
            it.type == productType(recived)
        }
        adapter.update(products)
        /*val btn = binding.btnForm
        goToForm(btn)*/

    }

    /*private fun goToForm(btn: Button) {
        val btnForm = binding.btnForm
        btnForm.setOnClickListener {
            val intent = Intent(this, ProductFormActivity::class.java)
            startActivity(intent)
            finish()
        }
    }*/

    private fun configureRecycler() {
        val recyclerView = binding.recyclerProduct
        recyclerView.adapter = adapter
    }

    private fun productType(str: String?): ProductType {
        if (str == "bebida") {
            return ProductType.BEBIDA
        }
        if (str == "comida") {
            return ProductType.COMIDA
        }
        if (str == "store") {
            return ProductType.STORE
        }
        if (str == "doce") {
            return ProductType.DOCE
        } else {
            return ProductType.NONE
        }
    }
}