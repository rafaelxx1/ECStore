package com.example.ecstore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.databinding.LoginFormBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { LoginFormBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btnLogin = binding.btnLogin
        btnLogin.setOnClickListener {
            login()
        }

    }


    private fun login(){
        val user = binding.txtLogin.text.toString()
        val password = binding.txtPassword.text.toString()

        val labelInfo = binding.labelInfo

        if(user == "adm" && password == "adm" ){
            labelInfo.text = "login valido."
            labelInfo.setTextColor(Color.GREEN)
            val intent = Intent(this, DashboardAdmActivity::class.java)
            this.startActivity(intent)
            finish()
        }else{
            labelInfo.text = "Usuário ou Senha invalidos."
            labelInfo.setTextColor(Color.RED)
        }
    }
}