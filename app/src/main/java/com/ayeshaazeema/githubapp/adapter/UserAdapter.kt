package com.ayeshaazeema.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.activity.DetailActivity
import com.ayeshaazeema.githubapp.databinding.ItemUserBinding
import com.ayeshaazeema.githubapp.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users = ArrayList<User>()

    fun setData(user: ArrayList<User>) {
        users.clear()
        users.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemUserBinding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            Glide.with(itemView.context).load(user.avatar).apply(RequestOptions().override(55, 55))
                .into(itemUserBinding.ivItemUser)

            itemUserBinding.tvItemUsername.text = user.username

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }
}