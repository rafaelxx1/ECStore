package com.example.ecstore.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ChangeProductImgViewBinding
import com.example.ecstore.model.Produto
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChangeProductImgActivity : AppCompatActivity() {
    private val binding by lazy { ChangeProductImgViewBinding.inflate(layoutInflater) }
    private val PICK_IMAGE_REQUEST = 1
    private var currentPhotoPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val idProduct = intent.getStringExtra("PRODUCT_ID")
        Log.i("ID RECEBIDO", idProduct.toString())
        val btnConfirm = binding.btnConfirm
        val btnCancel = binding.btnCancel
        val prod = getProduct(idProduct)
        Log.i("ID ENVIADO", prod.id.toString())

        btnCancel.setOnClickListener {
            val intent = Intent(this, ProductEditActivity::class.java)
            intent.putExtra("ID_PRODUCT", prod.id.toString())
            this.startActivity(intent)
            this.finish()
        }

        btnConfirm.setOnClickListener {
            galleryImg()
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

                val idProduct = intent.getStringExtra("PRODUCT_ID")
                val intent = Intent(this, ProductEditActivity::class.java)
                intent.putExtra("IMG_PATH", currentPhotoPath)
                intent.putExtra("ID_PRODUCT", idProduct)
                this.startActivity(intent)
                this.finish()

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error ao salvar a imagem", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun getProduct(id: String?): Produto{
        val db = ECStoreDataBase.getInstance(this)
        val productDao = db.ProductDao()

        return productDao.findById(id?.toLong())
    }
}