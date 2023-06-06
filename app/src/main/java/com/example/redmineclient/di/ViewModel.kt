package com.example.redmineclient.di

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow

interface ViewModel {
    val scope: CoroutineScope
    val jobs: MutableList<Job>

    fun onViewShown()
    fun onViewHidden()
    fun onCleared()
    fun validateResponse()
}

abstract class ViewModelImpl: ViewModel {
    override val scope = CoroutineScope(Dispatchers.Main)
    override val jobs: MutableList<Job> = mutableListOf()

    override fun onViewShown() {

    }

    override fun onViewHidden() {
        clearJobs()
    }

    override fun onCleared() {
        clearJobs()
    }

    override fun validateResponse() {

    }

    suspend fun exceptionHandleable(
        executionBlock: suspend () -> Unit,
        failureBlock: (suspend (exception: Throwable) -> Unit)? = null,
        completionBlock: (suspend () -> Unit)? = null
    ) {
        try {
            executionBlock()
        } catch (exception: Throwable) {
            if (exception is CancellationException) throw exception
            println("Throwable caught, cause: ${exception.cause}, message: ${exception.message}")
            failureBlock?.invoke(exception)
        } finally {
            completionBlock?.invoke()
        }
    }

    private fun clearJobs() {
        jobs.forEach {
            it.cancel()
        }
        jobs.clear()
    }

}

interface State<T> {
    val state: StateFlow<T>
}

interface StatefulViewModel<T> : State<T>, ViewModel {}

abstract class StatefulViewModelImpl<T> : StatefulViewModel<T>, ViewModelImpl() {}

class StatefulViewModelWrapper<T : StatefulViewModel<S>, S>(
    val viewModel: T
) : androidx.lifecycle.ViewModel() {
    val state = mutableStateOf(viewModel.state.value)

    init {
        viewModelScope.launch {
            viewModel.state.collect {
                state.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModel.onCleared()
    }
}