package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.UserListBinding
import com.example.ecstore.model.Global
import com.example.ecstore.ui.recyclerview.adapter.UserListAdapter

class RecyclerUserListView : AppCompatActivity() {
    private val binding by lazy { UserListBinding.inflate(layoutInflater) }
    private val adapter = UserListAdapter(this, emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRecyclerView()
        setContentView(binding.root)

        val btnUserForm = binding.btnAddUser
        val btnBack = binding.btnVoltar

        btnBack.setOnClickListener {
            val intent = Intent(this, DashboardAdmActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        btnUserForm.setOnClickListener {
            val intent = Intent(this, UserAddFormActivity::class.java)
            this.startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val db = ECStoreDataBase.getInstance(this)
        val userDao = db.UserDao()

        adapter.update(userDao.findAll())


    }


    private fun configureRecyclerView() {
        val recyclerView = binding.recyclerUserList
        recyclerView.adapter = adapter
    }
}