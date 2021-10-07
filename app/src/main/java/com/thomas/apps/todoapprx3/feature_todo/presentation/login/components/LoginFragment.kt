package com.thomas.apps.todoapprx3.feature_todo.presentation.login.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thomas.apps.todoapprx3.databinding.FragmentLoginBinding
import com.thomas.apps.todoapprx3.feature_todo.presentation.login.LoginEvent
import com.thomas.apps.todoapprx3.feature_todo.presentation.login.LoginViewModel
import com.thomas.apps.todoapprx3.utils.view.ActivityUtils.toast
import com.thomas.apps.todoapprx3.utils.view.ViewUtils.showIf
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setUpTextInput()

        setUpButton()

        observe()

        return binding.root
    }

    private fun setUpTextInput() {
        binding.textInputPassword.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {

                    viewModel.onEvent(LoginEvent.Login)
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpButton() {
        binding.buttonLogin.setOnClickListener {
            viewModel.onEvent(LoginEvent.Login)
        }
    }

    private fun observe() {
        viewModel.event
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ event ->
                when (event) {
                    is LoginViewModel.UIEvent.LoginSuccess -> {
                        val action = LoginFragmentDirections.actionLoginFragmentToTodosFragment()
                        findNavController().navigate(action)
                    }
                    is LoginViewModel.UIEvent.LoggingIn -> {
                        binding.loading.showIf(event.isLoggingIn)
                        binding.textInputLayoutUsername.isEnabled = event.isLoggingIn.not()
                        binding.textInputLayoutPassword.isEnabled = event.isLoggingIn.not()
                        binding.buttonLogin.isEnabled = event.isLoggingIn.not()
                    }
                    is LoginViewModel.UIEvent.ShowSnackbar -> {
                        toast(event.message)

                    }
                }
            }, {
                toast(it.message ?: "Unknown Error")
            })
    }
}