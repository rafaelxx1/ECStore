package com.example.ecstore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ProductConfirmationBinding
import com.example.ecstore.model.Global

class ProductConfirmationActivity : AppCompatActivity() {
    private val binding by lazy {ProductConfirmationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //binding.productConfirmationPRODUCTNAME.text = recived
    }

    override fun onResume() {
        super.onResume()

        val recived = intent.getStringExtra("product")
        val db = ECStoreDataBase.getInstance(this)
        val productDao = db.ProductDao()
        val userDao = db.UserDao()

        val idUser = Global.globalVar
        val user = userDao.findById(idUser.toLong())

        val product = recived?.toLong().let { productDao.findById(it) }
        binding.productConfirmationIMG.setImageURI(product?.img?.toUri())
        binding.productConfirmationPRODUCTNAME.text = product?.nome
        binding.productConfirmationPRODUCTPRICE.text = product?.price.toString()

        val btn = binding.productConfirmationBTN
        btn.setOnClickListener {
            binding.labelUser.text = "welcome: ${user.nome}"
        }
    }
}