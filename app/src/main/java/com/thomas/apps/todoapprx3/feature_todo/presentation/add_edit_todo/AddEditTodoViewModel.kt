package com.thomas.apps.todoapprx3.feature_todo.presentation.add_edit_todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.thomas.apps.todoapprx3.feature_todo.domain.model.Todo
import com.thomas.apps.todoapprx3.feature_todo.domain.use_case.TodoUseCases
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
class AddEditTodoViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _state = MutableStateFlow(AddEditTodoState())
    val state: StateFlow<AddEditTodoState> = _state

    private val _event = BehaviorSubject.create<UIEvent>()
    val event: Observable<UIEvent> = _event

    init {
        savedStateHandle.get<Long>("id")?.let { todoId ->
            if (todoId != -1L) {
                todoUseCases.getTodo(todoId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        {
                            _state.value = state.value.copy(title = it.title, content = it.content)
                        }, {
                            Timber.e(it)
                        }
                    )
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.SaveTodo -> {
                disposable?.dispose()
                disposable = todoUseCases.addTodo(
                    Todo(
                        title = state.value.title,
                        content = state.value.content,
                        timeStamp = System.currentTimeMillis(),
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        {
                            _event.onNext(UIEvent.SaveSuccess)
                        },
                        {
                            _event.onNext(UIEvent.ShowSnackbar(it.message ?: ""))
                        }
                    )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveSuccess : UIEvent()
    }
}