package com.thomas.apps.todoapprx3.feature_todo.presentation.login

import androidx.lifecycle.ViewModel
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login.LoginUseCases
import com.thomas.apps.todoapprx3.feature_todo.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _event = BehaviorSubject.create<UIEvent>()
    val event: Observable<UIEvent> = _event

    private var disposableLogin: Disposable? = null

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                disposableLogin?.dispose()
                disposableLogin = loginUseCases.login(state.value.username, state.value.password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({ resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                _event.onNext(UIEvent.LoggingIn(true))
                            }
                            is Resource.Success -> {
                                _event.onNext(UIEvent.LoggingIn(false))
                                _event.onNext(UIEvent.LoginSuccess)
                            }
                            is Resource.Error -> {

                            }
                        }
                    }, {
                        Timber.e("${it.message} - ${state.value}")
                        _event.onNext(UIEvent.LoggingIn(false))
                        _event.onNext(UIEvent.ShowSnackbar(it.message ?: "Unknown Error"))
                    })
            }
        }
    }

    override fun onCleared() {
        disposableLogin?.dispose()
        super.onCleared()
    }

    sealed class UIEvent {
        data class LoggingIn(val isLoggingIn: Boolean) : UIEvent()
        data class ShowSnackbar(val message: String) : UIEvent()
        object LoginSuccess : UIEvent()
    }
}