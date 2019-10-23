package io.kamara.githubers.ui

import androidx.recyclerview.widget.RecyclerView
import io.kamara.githubers.databinding.UserListItemBinding
import io.kamara.githubers.model.User

class UserViewHolder(private val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(newUser: User, userClickCallback: ((User) -> Unit)) {
        binding.apply {
            user = newUser
            executePendingBindings()
        }
        binding.root.setOnClickListener { userClickCallback.invoke(newUser) }
    }
}