package com.ayeshaazeema.githubapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayeshaazeema.githubapp.databinding.ItemUserBinding
import com.ayeshaazeema.githubapp.model.Users
import com.bumptech.glide.Glide
import com.ayeshaazeema.githubapp.R
import com.bumptech.glide.request.RequestOptions

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private val users = ArrayList<Users>()

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemUserBinding = ItemUserBinding.bind(itemView)

        fun bind(user: Users) {
            itemUserBinding.tvItemUsername.text = user.username
            Glide.with(itemView.context).load(user.avatar).apply(RequestOptions().override(55, 55))
                .into(itemUserBinding.ivItemUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}