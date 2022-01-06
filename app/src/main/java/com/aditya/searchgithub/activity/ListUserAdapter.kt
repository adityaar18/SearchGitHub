package com.aditya.searchgithub.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.searchgithub.data.User
import com.aditya.searchgithub.databinding.ItemListBinding
import com.bumptech.glide.Glide

class ListUserAdapter: RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val userList = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class ListViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindingList(user: User){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            Glide.with(itemView.context)
                .load(user.avatar_url)
                .circleCrop()
                .into(binding.imgItemAvatar)
            binding.tvItemName.text = user.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindingList(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    fun setListUser(user: ArrayList<User>){
        userList.clear()
        userList.addAll(user)
        notifyDataSetChanged()
    }
}