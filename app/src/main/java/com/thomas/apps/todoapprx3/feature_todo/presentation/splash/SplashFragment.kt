package com.thomas.apps.todoapprx3.feature_todo.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thomas.apps.todoapprx3.databinding.FragmentSplashBinding
import com.thomas.apps.todoapprx3.feature_todo.data.data_source.utils.Cache.getToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        checkLoginState()
    }

    private fun checkLoginState() {
        requireContext().getToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ token ->
                val action = if (token.isEmpty()) {
                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                } else {
                    SplashFragmentDirections.actionSplashFragmentToTodosFragment()
                }
                findNavController().navigate(action)
            }, {
                Timber.e(it)
                //toast(it.message?: "Unknown Error")
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                findNavController().navigate(action)
            })
    }
}