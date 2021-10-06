package com.thomas.apps.todoapprx3.feature_todo.presentation.todos.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.thomas.apps.todoapprx3.R
import com.thomas.apps.todoapprx3.databinding.FragmentTodosBinding
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.presentation.todos.TodosViewModel
import com.thomas.apps.todoapprx3.feature_todo.presentation.utils.SpacingItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodosFragment : Fragment() {

    private lateinit var binding: FragmentTodosBinding

    private val viewModel: TodosViewModel by viewModels()

    private val todoItemAdapter by lazy {
        TodoItemAdapter().apply {
            deleteClickListener = object : TodoItemAdapter.DeleteClickListener {
                override fun onDelete(todo: Todo) {
//                    viewModel.onEvent(TodosEvent.DeleteTodo(todo))
//                    showDeleteSnackbar()
                }
            }
            itemClickListener = object : TodoItemAdapter.ItemClickListener {
                override fun onClick(todo: Todo) {
//                    val action = TodosFragmentDirections.actionTodosFragmentToAddEditTodoFragment(
//                        todoId = todo.id ?: -1,
//                        todoColor = todo.color
//                    )
//                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodosBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        setUpFab()

        observe()

        return binding.root
    }

    private fun observe() {
        viewModel.state
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.todosFragment))

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_logout -> {
                    showSignOutDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpFab() {
        binding.fabAddTodo.setOnClickListener {
//            val action = TodosFragmentDirections.actionTodosFragmentToAddEditTodoFragment(
//                todoId = -1,
//            )
//            findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoItemAdapter
            addItemDecoration(SpacingItemDecorator.DEFAULT)
            setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                when {
                    scrollY > oldScrollY -> {
                        binding.fabAddTodo.hide()
                    }
                    scrollY < oldScrollY -> {
                        binding.fabAddTodo.show()
                    }
                }
            }
        }
    }

    private fun showDeleteSnackbar() {
        Snackbar.make(binding.root, R.string.todo_deleted, Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.fabAddTodo)
            .show()
    }

    private fun showSignOutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.logout)
            .setMessage(R.string.sign_out_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                //viewModel.onEvent(TodosEvent.SignOut)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}