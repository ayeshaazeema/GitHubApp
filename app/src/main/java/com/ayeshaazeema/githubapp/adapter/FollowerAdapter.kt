package com.ayeshaazeema.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayeshaazeema.githubapp.databinding.ItemUserBinding
import com.ayeshaazeema.githubapp.model.User
import com.bumptech.glide.Glide
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.activity.DetailActivity
import com.bumptech.glide.request.RequestOptions

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private val users = ArrayList<User>()

    fun setData(user: ArrayList<User>) {
        users.clear()
        users.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowerViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemUserBinding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            itemUserBinding.tvItemUsername.text = user.username

            Glide.with(itemView.context).load(user.avatar).apply(RequestOptions().override(55, 55))
                .into(itemUserBinding.ivItemUser)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }
}