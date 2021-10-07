package com.thomas.apps.todoapprx3.feature_todo.presentation.todos

import androidx.lifecycle.ViewModel
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.login.LoginUseCases
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.todos.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    private val loginUseCases: LoginUseCases,
) : ViewModel() {

    private var disposable: Disposable? = null
    private val _state = BehaviorSubject.createDefault(TodoState())
    val state: Observable<TodoState> = _state

    private val _event = BehaviorSubject.create<UIEvent>()
    val event: Observable<UIEvent> = _event

    init {
        getTodos()
    }

    fun onEvent(event: TodosEvent) {
        when (event) {
            is TodosEvent.DeleteTodo -> {
                todoUseCases.deleteTodo(event.todo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                    }, {
                        _event.onNext(UIEvent.Snackbar(it.message ?: "Unknown Error"))
                    })

            }
            is TodosEvent.Logout -> {
                loginUseCases.logout()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        _event.onNext(UIEvent.Logout)

                    }, {
                        _event.onNext(UIEvent.Snackbar(it.message ?: "Unknown Error"))
                    })
            }
        }
    }

    private fun getTodos() {
        disposable = todoUseCases.getTodos()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                _state.onNext(
                    _state.value?.copy(todos = it) ?: TodoState()
                )
            }
    }

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }

    sealed class UIEvent {
        object Logout : UIEvent()
        data class Snackbar(val msg: String) : UIEvent()
    }
}