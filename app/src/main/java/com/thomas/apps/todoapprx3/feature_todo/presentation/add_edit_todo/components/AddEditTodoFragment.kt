package com.thomas.apps.todoapprx3.feature_todo.presentation.add_edit_todo.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.thomas.apps.todoapprx3.R
import com.thomas.apps.todoapprx3.databinding.FragmentAddEditTodoBinding
import com.thomas.apps.todoapprx3.feature_todo.presentation.add_edit_todo.AddEditTodoEvent
import com.thomas.apps.todoapprx3.feature_todo.presentation.add_edit_todo.AddEditTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class AddEditTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddEditTodoBinding

    private val viewModel: AddEditTodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditTodoBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        observe()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val navController = findNavController()
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.todosFragment))

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_save -> {
                    viewModel.onEvent(AddEditTodoEvent.SaveTodo)
                    true
                }
                else -> false
            }
        }
    }

    private fun observe() {
        viewModel.event
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                Timber.i("onNext $event")
                when (event) {
                    is AddEditTodoViewModel.UIEvent.SaveSuccess -> {
                        findNavController().popBackStack()
                    }
                    is AddEditTodoViewModel.UIEvent.ShowSnackbar -> {
                        showSnackbar(event.message)
                    }
                }
            }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}