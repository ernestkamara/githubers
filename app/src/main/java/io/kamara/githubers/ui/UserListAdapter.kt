package io.kamara.githubers.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.kamara.githubers.databinding.UserListItemBinding
import io.kamara.githubers.model.User

class UserListAdapter(private val userClickCallback: ((User) -> Unit)) : ListAdapter<User, RecyclerView.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = getItem(position)
        (holder as UserViewHolder).bind(user, userClickCallback)
    }


    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem && oldItem.avatarUrl == newItem.avatarUrl
        }
    }
}
