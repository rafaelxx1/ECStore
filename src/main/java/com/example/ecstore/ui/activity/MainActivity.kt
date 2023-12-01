package com.example.ecstore.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.R
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ActivityMainBinding
import com.example.ecstore.model.Global

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Global.globalVar = ""
        val login = binding.loginView
        login.setOnClickListener {
            goToLogin()
        }

        btnClick()
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        Global.globalVar = ""
        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()
        val btn = binding.btnBuscar
        val label = binding.labelFind

        btn.setOnClickListener {
            val cpf = binding.cpf.text.toString()
            val user = userDao.findAll()
            val userFind = user.find {
                it.cpf == cpf
            }


            Log.i("FOUNDED USER", userFind.toString())

            if (userFind?.cpf != "" && userFind != null && userFind?.active_yn != false) {
                label.text = "Usuário encontrado: ${userFind?.nome}"
                val intent = Intent(this, ProductSelectionActivity::class.java)
                this.startActivity(intent)
                Global.globalVar = userFind?.id.toString()
                finish()
            } else {
                label.text = "Usuário inexistente, ou bloqueado."
            }


        }
    }


    private fun btnClick() {
        val cpf: TextView = binding.cpf
        val btn: Button = binding.btnBuscar
        val title: TextView = binding.titleFind

        btn.setOnClickListener {
            title.text = cpf.text
            //val intent = Intent(this, )
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        this.startActivity(intent)
        finish()
    }

}