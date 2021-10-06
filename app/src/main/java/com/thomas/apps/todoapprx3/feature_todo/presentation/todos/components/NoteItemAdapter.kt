package com.thomas.apps.todoapprx3.feature_todo.presentation.todos.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thomas.apps.todoapprx3.databinding.ItemTodoBinding
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo

class TodoItemAdapter : ListAdapter<Todo, TodoItemAdapter.ViewHolder>(TodoDC()) {

    var deleteClickListener: DeleteClickListener? = null
    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.deleteClickListener = deleteClickListener
        holder.itemClickListener = itemClickListener
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var deleteClickListener: DeleteClickListener? = null
        var itemClickListener: ItemClickListener? = null

        fun bind(item: Todo) {
            with(binding) {
                textViewTitle.text = item.title
                textViewContent.text = item.content

                imageViewDelete.setOnClickListener {
                    deleteClickListener?.onDelete(item)
                }

                root.setOnClickListener { itemClickListener?.onClick(item) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    private class TodoDC : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    interface DeleteClickListener {
        fun onDelete(todo: Todo)
    }

    interface ItemClickListener {
        fun onClick(todo: Todo)
    }
}