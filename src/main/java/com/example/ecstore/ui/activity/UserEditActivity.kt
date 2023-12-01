package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.UserEditViewBinding
import com.example.ecstore.model.User
import com.google.android.material.textfield.TextInputEditText

class UserEditActivity : AppCompatActivity() {
    private val binding by lazy { UserEditViewBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)

        val btnVoltar = binding.btnCancelar
        btnVoltar.setOnClickListener {
            val intent = Intent(this, DashboardAdmActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }


    }

    override fun onResume() {
        super.onResume()

        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()

        val intent = intent.getStringExtra("USER_ID_EDIT")
        Log.i("ID", intent.toString())
        val nome = binding.editTxtName
        val cpf = binding.editTxtCpf
        val email = binding.editTxtEmail

        val user = takeUser(intent)
        val btnEdit = binding.btnEditar

        //Log.i("**USER**", user.toString())
        if(user != null){

            nome.text = Editable.Factory.getInstance().newEditable(user.nome)
            cpf.text = Editable.Factory.getInstance().newEditable(user.cpf)
            email.text = Editable.Factory.getInstance().newEditable(user.email)
        }else{
            binding.labelInfo.text = "Usuário não encontrado"
        }


        btnEdit.setOnClickListener {
            if(user != null) {
                userDao.update(
                    updateUser(
                        user,
                        nome.text.toString(),
                        cpf.text.toString(),
                        email.text.toString()
                    )
                )

                val intent = Intent(this, RecyclerUserListView::class.java)
                this.startActivity(intent)
                finish()

            }else{
                binding.labelInfo.text = "Usuário não encontrado"
            }
        }
    }


    private fun takeUser(id: String?): User{
        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()

        val idLong = id?.toLong()
        return userDao.findById(idLong)
    }


    private fun updateUser(user: User, nome: String, cpf: String, email: String): User{

        return User(id = user.id, nome = nome, cpf = cpf, email = email, active_yn = user.active_yn)

    }
}