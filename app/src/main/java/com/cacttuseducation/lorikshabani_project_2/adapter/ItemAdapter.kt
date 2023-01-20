package com.cacttuseducation.lorikshabani_project_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cacttuseducation.lorikshabani_project_2.R
import com.cacttuseducation.lorikshabani_project_2.ui.blog.BlogFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class ItemAdapter(private var items: List<BlogFragment.Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    interface OnItemAddedToCartListener {
        fun onItemAddedToCart(item: BlogFragment.Item)
    }

    private var listener: OnItemAddedToCartListener? = null

    fun setOnItemAddedToCartListener(listener: OnItemAddedToCartListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemRating.text = "Rating: ${item.rating}"
        holder.itemPrice.text = "Price: ${item.price}$"
        Picasso.get().load(item.image).into(holder.itemImage)

        val mutableItems = items.toMutableList()
        holder.itemAddToCart.setOnClickListener {
            mutableItems.remove(item)
            items = mutableItems
            notifyDataSetChanged()
            Snackbar.make(holder.itemView, "Item added to cart", Snackbar.LENGTH_SHORT).show()
        }



    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.tvName)
        val itemRating: TextView = itemView.findViewById(R.id.tvRating)
        val itemPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val itemImage: ImageView = itemView.findViewById(R.id.ivImage)
        val itemAddToCart: ImageButton = itemView.findViewById(R.id.btnAddToCart)
    }
}
