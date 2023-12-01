package com.example.ecstore.ui.recyclerview.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.ecstore.R
import com.example.ecstore.database.ECStoreDataBase
import com.example.ecstore.databinding.ProductItemBinding
import com.example.ecstore.databinding.UserItemViewBinding
import com.example.ecstore.databinding.UserListBinding
import com.example.ecstore.model.User
import com.example.ecstore.ui.activity.RecyclerUserListView
import com.example.ecstore.ui.activity.UserEditActivity

class UserListAdapter(
    private val context: Context,
    user: List<User>,
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val user = user.toMutableList()

    class ViewHolder(
        private val context: Context,
        binding: UserItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val name = binding.txtName
        private val btnEdit = binding.btnEditUser
        private val btnBlock = binding.btnBlockUser
        private val layout = binding.linearLayout

        fun putIt(user: User) {
            name.text = user.nome
            val userId = user.id.toString()
            val drawbleUnlock = ContextCompat.getDrawable(context, R.drawable.unlock)
            val drawbleBlock = ContextCompat.getDrawable(context, R.drawable.block)

            if(user.active_yn){

            }else{
                btnBlock.setImageDrawable(drawbleUnlock)
                layout.setBackgroundColor(Color.parseColor("#FDA5B0"))
            }

            btnEdit.setOnClickListener {
                btnEdit.text = "clicked"
                val intent = Intent(context, UserEditActivity::class.java)
                intent.putExtra("USER_ID_EDIT", userId)
                context.startActivity(intent)

                (context as Activity).finish()
            }

            btnBlock.setOnClickListener {
                val db = ECStoreDataBase.getInstance(context)
                val userDao = db.UserDao()
                val user = userDao.findById(user.id)

                if (user.active_yn) {
                    val userBlock = User(
                        id = user.id,
                        nome = user.nome,
                        cpf = user.cpf,
                        email = user.email,
                        active_yn = false
                    )
                    userDao.update(userBlock)
                    btnBlock.setImageDrawable(drawbleUnlock)
                    layout.setBackgroundColor(Color.parseColor("#FDA5B0"))
                }else{
                    val userBlock = User(
                        id = user.id,
                        nome = user.nome,
                        cpf = user.cpf,
                        email = user.email,
                        active_yn = true
                    )
                    userDao.update(userBlock)
                    btnBlock.setImageDrawable(drawbleBlock)
                    layout.setBackgroundColor(Color.WHITE)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemViewBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = user[position]
        holder.putIt(user)
    }

    fun update(user: List<User>) {
        this.user.clear()
        this.user.addAll(user)
        notifyDataSetChanged()
    }

}