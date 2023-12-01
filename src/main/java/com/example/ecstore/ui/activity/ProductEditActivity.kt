package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ProductEditViewBinding
import com.example.ecstore.model.ProductType
import com.example.ecstore.model.Produto

class ProductEditActivity : AppCompatActivity() {
    private val binding by lazy { ProductEditViewBinding.inflate(layoutInflater) }
    private lateinit var selectedValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinner = binding.spinner
        val spinnerAdapter = spinnerArray()
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemSelected = p0?.getItemAtPosition(p2) as String

                selectedValue = itemSelected

                if (selectedValue != "SELECIONAR") {
                    Toast.makeText(
                        this@ProductEditActivity,
                        "Item selecionado: $selectedValue",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val idProduct = intent.getStringExtra("ID_PRODUCT")
        val imgPath = intent.getStringExtra("IMG_PATH")

        val product = getProduct(idProduct)
        Log.i("PRODUTO", product.toString())

        val btnEditImg = binding.imgProduct

        btnEditImg.setOnClickListener {
            val intent = Intent(this, ChangeProductImgActivity::class.java )
            intent.putExtra("PRODUCT_ID", product.id.toString())
            this.startActivity(intent)
            this.finish()
        }

        binding.productName.text = Editable.Factory.getInstance().newEditable(product.nome)
        binding.productPrice.text =
            Editable.Factory.getInstance().newEditable(product.price.toString())
        binding.spinner.setSelection(spinnerSelection(product.type.toString()))
        binding.imgProduct.setImageURI(product.img?.toUri())


        if (imgPath != "" && imgPath != null) {
            binding.imgProduct.setImageURI(imgPath.toUri())
        }



    }

    private fun validateForm(name: String, price: String, type: String): Boolean {
        return name != "" && price != null && type != "SELECIONAR"
    }

    override fun onResume() {
        val imgPath = intent.getStringExtra("IMG_PATH")
        val idProduct = intent.getStringExtra("ID_PRODUCT")
        super.onResume()
        val btnEdit = binding.btnEditSave

        btnEdit.setOnClickListener {
            val name = binding.productName.text.toString()
            val price = binding.productPrice.text.toString()
            val type = productType(selectedValue)
            val img = imgPath
            if (validateForm(name = name, price = price, type = type.toString())) {
                val finalProd = newProduct(
                    imgPath = img,
                    newNome = name,
                    newPrice = price,
                    newType = type,
                    prod = getProduct(idProduct)
                )
                Log.i("NEW PRODUCT", finalProd.toString())
                updateProd(finalProd)
                val intent = Intent(this, RecyclerProductAdmView::class.java)
                this.startActivity(intent)
                this.finish()

            } else {
                binding.labelInfo.text = "preencher campos."
            }
        }
    }

    private fun spinnerArray(): ArrayAdapter<String> {
        val spinnerItem = android.R.layout.simple_spinner_item
        val spinnerDropDown = android.R.layout.simple_spinner_dropdown_item

        val listOp: List<String> = listOf("SELECIONAR", "bebida", "doce", "comida", "store")

        val spinnerAdapter = ArrayAdapter<String>(this, spinnerItem, listOp)
        spinnerAdapter.setDropDownViewResource(spinnerDropDown)

        return spinnerAdapter
    }


    private fun newProduct(
        imgPath: String?,
        newNome: String,
        newPrice: String,
        newType: ProductType,
        prod: Produto
    ): Produto {
        var imgPathCondition: String? = ""
        if (imgPath == "" && imgPath.isEmpty()) {
            imgPathCondition = prod.img
        } else {
            imgPathCondition = imgPath
        }

        return Produto(
            id = prod.id,
            nome = newNome,
            price = newPrice.toBigDecimal(),
            img = imgPathCondition,
            type = newType
        )
    }

    private fun productType(str: String): ProductType {
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

    private fun getProduct(id: String?): Produto {
        val db = ECStoreDataBase.getInstance(this)
        val productDao = db.ProductDao()
        Log.i("TYPE", productDao.findById(id?.toLong()).type.toString())
        return productDao.findById(id?.toLong())
    }

    private fun spinnerSelection(spinner: String): Int {
        if (spinner == ProductType.BEBIDA.toString()) {
            return 1
        }
        if (spinner == ProductType.COMIDA.toString()) {
            return 3
        }
        if (spinner == ProductType.STORE.toString()) {
            return 4
        }
        if (spinner == ProductType.DOCE.toString()) {
            return 2
        } else {
            return 0
        }
    }

    private fun updateProd(prod: Produto) {
        val db = ECStoreDataBase.getInstance(this)
        val productDao = db.ProductDao()

        productDao.update(prod)
    }
}