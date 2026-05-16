package com.kelompok.satwalaya

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompok.satwalaya.model.PostResponse

class PostAdapter(private var items: List<PostResponse>) :
    RecyclerView.Adapter<PostAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvPostId: TextView = view.findViewById(R.id.tvPostId)
        val tvPostTitle: TextView = view.findViewById(R.id.tvPostTitle)
        val tvPostBody: TextView = view.findViewById(R.id.tvPostBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvPostId.text = "Post #${item.id}"
        holder.tvPostTitle.text = item.title
        holder.tvPostBody.text = item.body
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<PostResponse>) {
        items = newItems
        notifyDataSetChanged()
    }
}