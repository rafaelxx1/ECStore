package com.example.ecstore.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ActivityProductFormBinding
import com.example.ecstore.model.ProductType
import com.example.ecstore.model.Produto
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductFormActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProductFormBinding.inflate(layoutInflater) }
    private lateinit var selectedValue: String

    private val PICK_IMAGE_REQUEST = 1
    private var currentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureBtnSave()
        //dialogImg()
        val btnVoltar = binding.btnVoltar
        val spinner = binding.spinner
        val spinnerAdapter = spinnerArray()
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val itemSelected = p0?.getItemAtPosition(p2) as String

                selectedValue = itemSelected

                if (selectedValue != "SELECIONAR") {
                    Toast.makeText(
                        this@ProductFormActivity,
                        "Item selecionado: $selectedValue",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }


        btnVoltar.setOnClickListener {
            val intent = Intent(this, DashboardAdmActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }


    }

    override fun onResume() {
        super.onResume()

    }

    private fun spinnerArray(): ArrayAdapter<String> {
        val spinnerItem = android.R.layout.simple_spinner_item
        val spinnerDropDown = android.R.layout.simple_spinner_dropdown_item

        val listOp: List<String> = listOf("SELECIONAR", "bebida", "doce", "comida", "store")

        val spinnerAdapter = ArrayAdapter<String>(this, spinnerItem, listOp)
        spinnerAdapter.setDropDownViewResource(spinnerDropDown)

        return spinnerAdapter
    }

    private fun createFormProd(img: String?) {
        val db = ECStoreDataBase.getInstance(this)

        val productDao = db.ProductDao()

        val newProd = createProduct(img)
        productDao.save(newProd)

        val intent = Intent(this, DashboardAdmActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateForm() {
        val txtFormProduct = binding.formTextProduct.text.toString()
        val txtFormPrice = binding.formTextPrice.text.toString()
        val btn = binding.pfBtnRegister
        val labelInfo = binding.labelInfo

        if (selectedValue == "SELECIONAR") {
            labelInfo.text = "selecionar categoria do produto"
            labelInfo.setTextColor(Color.RED)
        } else {
            if (txtFormPrice.isEmpty() || txtFormPrice.isBlank() || txtFormPrice == ""
                || txtFormProduct.isBlank() || txtFormProduct.isEmpty() || txtFormProduct == ""
            ) {
                labelInfo.text ==  "preencher todos os campos"
                Log.i("MSG:", "PREENCHA O FORMULARIO")
            } else {
                galleryImg()
            }
        }
    }


    private fun galleryImg() {
        val galeriaIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(
            Intent.createChooser(galeriaIntent, "Escolha sua img"),
            PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImg = data.data
            try {
                currentPhotoPath = saveImage(selectedImg)
                Toast.makeText(this, "Img salva em $currentPhotoPath", Toast.LENGTH_SHORT).show()
                createFormProd(currentPhotoPath)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error ao salvar a imagem", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureBtnSave() {
        val btnRegister = binding.pfBtnRegister
        btnRegister.setOnClickListener {
            validateForm()
        }
    }

    private fun createProduct(img: String?): Produto {
        val productName = binding.formTextProduct
        val name = productName.text.toString()

        val productPrice = binding.formTextPrice
        val price = productPrice.text.toString()

        return Produto(null, name, price.toBigDecimal(), img, productType(selectedValue))
    }


    @Throws(IOException::class)
    private fun saveImage(selectedImg: android.net.Uri?): String {
        val inputStream = contentResolver.openInputStream(selectedImg!!)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "JPEG_${timeStamp}.jpg"
        val directory = getDir("imgProduct", MODE_PRIVATE)
        val file = File(directory, fileName)

        currentPhotoPath = file.absolutePath

        file.outputStream().use { output ->
            inputStream?.copyTo(output)
        }

        return currentPhotoPath!!
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

}