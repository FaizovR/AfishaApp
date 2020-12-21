package ru.faizovr.afisha.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faizovr.afisha.R
import ru.faizovr.afisha.presentation.viewholder.CategoryItemViewHolder

class CategoryAdapter(private val onMenuClicked: (position: Int) -> Unit) :
    RecyclerView.Adapter<CategoryItemViewHolder>() {

    private var menuList: List<String> = listOf()

    fun updateList(newList: List<String>) {
        menuList = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item_view_holder, parent, false)
        return CategoryItemViewHolder(view)
    }

    override fun getItemCount(): Int =
        menuList.size

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(menuList[position], onMenuClicked)
    }
}