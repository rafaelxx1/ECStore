package com.example.ecstore.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.DashboardAdmBinding

class DashboardAdmActivity : AppCompatActivity() {
    private val binding by lazy { DashboardAdmBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()
        val btnUser = binding.boxUser
        val btnProduct = binding.boxProduct
        val btnLogout = binding.txtLogout

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }

        goToUserList(btnUser)
        goToProductForm(btnProduct)

        val intentValue = intent.getStringExtra("CPF_USER")

        if(intentValue != "" && intentValue != null) {
            val user = userDao.findAll().find{
                it.cpf == intentValue
            }

            showDialog(this, "INFO", "Usuário: ${user?.nome.toString()}, criado com sucesso.")

        }else{}



    }

    private fun goToUserList(btn: ImageView){
        btn.setOnClickListener {
            val intent = Intent(this, RecyclerUserListView::class.java)
            this.startActivity(intent)
            finish()
        }
    }

    private fun goToUserForm(btn: ImageView){
        btn.setOnClickListener {

            val intent = Intent(this, UserAddFormActivity::class.java )
            this.startActivity(intent)
            this.finish()
        }
    }
    private fun goToProductForm(btn: ImageView){

        btn.setOnClickListener {
            val intent = Intent(this, RecyclerProductAdmView::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }

    private fun showDialog(context: Context, title: String, msg: String ){
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle(title)
        dialog.setMessage(msg)

        dialog.setPositiveButton("OK") {_, _ ->}

        val alert = dialog.create()
        alert.show()
    }
}