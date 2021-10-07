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
import com.thomas.apps.todoapprx3.R
import com.thomas.apps.todoapprx3.databinding.FragmentTodosBinding
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.presentation.todos.TodosEvent
import com.thomas.apps.todoapprx3.feature_todo.presentation.todos.TodosViewModel
import com.thomas.apps.todoapprx3.feature_todo.presentation.utils.SpacingItemDecorator
import com.thomas.apps.todoapprx3.utils.view.ActivityUtils.toast
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class TodosFragment : Fragment() {

    private lateinit var binding: FragmentTodosBinding

    private val viewModel: TodosViewModel by viewModels()

    private val todoItemAdapter by lazy {
        TodoItemAdapter().apply {
            deleteClickListener = object : TodoItemAdapter.DeleteClickListener {
                override fun onDelete(todo: Todo) {
                    viewModel.onEvent(TodosEvent.DeleteTodo(todo))
                }
            }
            itemClickListener = object : TodoItemAdapter.ItemClickListener {
                override fun onClick(todo: Todo) {
                    val action = TodosFragmentDirections.actionTodosFragmentToAddEditTodoFragment(
                        id = todo.id ?: -1
                    )
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("onCreate ${this.hashCode()}")
    }

    override fun onDestroy() {
        Timber.i("onDestroy ${this.hashCode()}")

        super.onDestroy()
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    todoItemAdapter.submitList(it.todos)
                }, {
                    Timber.e(it)
                    toast(it.message ?: "Unknown Error")
                }
            )
        viewModel.event
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ event ->
                when (event) {
                    is TodosViewModel.UIEvent.Logout -> {
                        Timber.i("logout ")
                        val action = TodosFragmentDirections.actionTodosFragmentToSplashFragment()
                        findNavController().navigate(action)
                    }
                    is TodosViewModel.UIEvent.Snackbar -> {
                        toast(event.msg)
                    }
                }
            }, {
                Timber.e(it)
                //toast(it.message ?: "Unknown Error")
            })
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
            val action = TodosFragmentDirections.actionTodosFragmentToAddEditTodoFragment(id = -1)
            findNavController().navigate(action)
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

    private fun showSignOutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.logout)
            .setMessage(R.string.sign_out_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.onEvent(TodosEvent.Logout)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}