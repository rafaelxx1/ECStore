package com.example.ecstore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.UserAddFormBinding
import com.example.ecstore.model.User

class UserAddFormActivity : AppCompatActivity() {
    private val binding by lazy { UserAddFormBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val btnCancel = binding.btnCancel
        btnCancel.setOnClickListener {
            val intent = Intent(this, DashboardAdmActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val btn = binding.btnSave
        btn.setOnClickListener {
            addUser()
        }

    }


    private fun addUser() {
        val name = binding.idName.text.toString()
        val cpf = binding.idCpf.text.toString()
        val email = binding.idEmail.text.toString()

        var user: User = User(null, "", "", "")


        if (name.length > 20 || cpf.length > 11 || cpf.length < 11 || email.length > 38) {
            binding.labelInfo.setTextColor(Color.RED)
            binding.labelInfo.text = """
               Name: MAX 20 caracteres 
               CPF:  11 Digitos
               Email: MAX 38 caracteres
           """.trimIndent()
        } else {

             user = User(nome = name, cpf = cpf, email = email)
            val db = ECStoreDataBase.getInstance(this)
            val userDao = db.UserDao()



            if (verifyCpf(user.cpf)) {
                binding.labelInfo.setTextColor(Color.GRAY)
                binding.labelInfo.text = "CPF já cadastrado."
            } else {
                if (name != "" && cpf != "" && email != "" && name != null && cpf != null && email != null) {
                    userDao.save(user)
                    goToDashBoard(cpf)
                } else {
                    binding.labelInfo.setTextColor(Color.RED)
                    binding.labelInfo.text = "Preencher corretamente os campos."
                }
            }

        }
    }

    private fun goToDashBoard(cpf: String) {
        val intent = Intent(this, DashboardAdmActivity::class.java)
        intent.putExtra("CPF_USER", cpf)
        this.startActivity(intent)
        this.finish()
    }

    private fun verifyCpf(cpf: String): Boolean {
        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()

        val users = userDao.findAll().find {
            it.cpf == cpf
        }

        return users != null

    }
}