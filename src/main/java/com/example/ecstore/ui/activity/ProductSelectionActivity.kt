package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ProductSelectionViewBinding
import com.example.ecstore.model.Global

class ProductSelectionActivity : AppCompatActivity() {
    private val binding by lazy { ProductSelectionViewBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()

        val btnSair = binding.txtLogout
        val btnDrink = binding.btnDrink
        val btnLunch = binding.btnLunch
        val btnDulce = binding.btnDulce
        val btnStore = binding.btnStore
        val labelUser = binding.labelUser
        val userId = Global.globalVar
        val user = userDao.findById(userId.toLong())

        labelUser.text = user.nome


        btnSair.setOnClickListener {
            exit()
        }

        btnDrink.setOnClickListener {
            goToDrinks()
        }

        btnLunch.setOnClickListener {
            goToLunch()
        }

        btnDulce.setOnClickListener {
            goToDulce()
        }

        btnStore.setOnClickListener {
            goToStore()
        }
    }

    private fun exit(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

    private fun goToDrinks() {
        val intent = Intent(this, RecyclerProductView::class.java)
        intent.putExtra("type_product", "bebida")
        this.startActivity(intent)
        this.finish()
    }

    private fun goToLunch() {
        val intent = Intent(this, RecyclerProductView::class.java)
        intent.putExtra("type_product", "comida")
        this.startActivity(intent)
        this.finish()
    }

    private fun goToDulce() {
        val intent = Intent(this, RecyclerProductView::class.java)
        intent.putExtra("type_product", "doce")
        this.startActivity(intent)
        this.finish()
    }

    private fun goToStore() {
        val intent = Intent(this, RecyclerProductView::class.java)
        intent.putExtra("type_product", "store")
        this.startActivity(intent)
        this.finish()
    }
}