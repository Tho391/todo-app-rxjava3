package com.thomas.apps.todoapprx3.feature_todo.presentation.todos

import androidx.lifecycle.ViewModel
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _state = BehaviorSubject.createDefault(TodoState())
    val state: Observable<TodoState> = _state

    init {
        getTodos()
    }

    private fun getTodos() {
        disposable = todoUseCases.getTodos().subscribeOn(Schedulers.io()).subscribe {
            _state.onNext(
                _state.value?.copy(todos = it) ?: TodoState()
            )
        }
    }

    override fun onCleared() {
        disposable?.dispose()

        super.onCleared()
    }
}