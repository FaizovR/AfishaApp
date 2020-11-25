package ru.faizovr.afisha.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_list_item_view_holder.view.*

class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: String, onMenuClicked: (position: Int) -> Unit) {
        itemView.text_menu_item_title.text = message
        itemView.setOnClickListener {
            onMenuClicked(adapterPosition)
        }
    }
}