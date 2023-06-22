package com.example.redmineclient.presentation.ui.timeEntries.viewModel

import com.example.redmineclient.data.storage.preferences.PreferencesStore
import com.example.redmineclient.di.StatefulViewModel
import com.example.redmineclient.di.StatefulViewModelImpl
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequest
import com.example.redmineclient.domain.models.timeEntries.TimeEntriesRequestParameters
import com.example.redmineclient.domain.state.LoadingState
import com.example.redmineclient.domain.state.StatusResponse
import com.example.redmineclient.domain.usecase.timeEntries.TimeEntriesUseCase
import com.example.redmineclient.presentation.navigator.Navigator
import com.example.redmineclient.presentation.ui.timeEntries.state.TimeEntriesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.time.LocalDate

interface TimeEntriesViewModel: StatefulViewModel<TimeEntriesState> {
    override val state: StateFlow<TimeEntriesState>
    val timeEntriesUseCase: TimeEntriesUseCase
    val preferencesStore: PreferencesStore
    fun onRefreshClick()
    fun onCancelClick()
    fun onSaveClick()
    fun saveEdit()
    fun saveCreate()
    fun chooseDate(date: LocalDate)
    fun getTimeEntry()
    fun onNumberValueChange(id: String)
    fun onActivityClick(value: Int)
    fun onValueChangeComment(value: String)
    fun chooseHours(value: Int)
    fun chooseMinutes(value: Int)
    fun setTotalHours(value: Double)
}

class TimeEntriesViewModelImpl(
    private val navigator: Navigator,
    override val preferencesStore: PreferencesStore,
    override val timeEntriesUseCase: TimeEntriesUseCase,
    private val time_entry_id: Int?
): KoinComponent, StatefulViewModelImpl<TimeEntriesState>(), TimeEntriesViewModel {

    private val mutableState = MutableStateFlow(
        TimeEntriesState()
    )

    override val state: StateFlow<TimeEntriesState>
        get() = mutableState

    override fun onViewShown() {
        super.onViewShown()
        if (time_entry_id != null) {
            getTimeEntry()
        } else {
            mutableState.update {
                it.copy(
                    loadingState = LoadingState.Success
                )
            }
        }
    }

    override fun onRefreshClick() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                mutableState.update {
                    it.copy(
                        loadingState = LoadingState.Loading
                    )
                }
            })
        })
    }

    override fun onCancelClick() {
        navigator.navigateBack()
    }

    override fun onSaveClick() {
        if (time_entry_id == null) {
            saveCreate()
        } else {
            saveEdit()
        }
    }

    override fun saveEdit() {

    }

    override fun saveCreate() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { api_key ->
                    mutableState.update {
                        it.copy(
                            loadingState = LoadingState.Loading
                        )
                    }
                    val response = timeEntriesUseCase.postTimeEntries(
                        api_key = api_key,
                        timeEntriesRequest = TimeEntriesRequest(
                            TimeEntriesRequestParameters(
                                issue_id = state.value.numberTask.toInt(),
                                time_entry_id = time_entry_id,
                                spent_on = state.value.spentOn,
                                activity_id = state.value.activity_id,
                                hours = state.value.hoursTotal,
                                comments = state.value.comments
                            )
                        )
                    )
                    when (response.statusResponse) {
                        StatusResponse.Created -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Success
                                )
                            }
                            navigator.navigateBack()
                        }

                        else -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Error
                                )
                            }
                        }
                    }
                }
            })
        })
    }

    override fun chooseDate(date: LocalDate) {
        mutableState.update {
            it.copy(
                date = date
            )
        }
    }

    override fun getTimeEntry() {
        jobs.add(scope.launch {
            exceptionHandleable(executionBlock = {
                preferencesStore.getToken().collect { api_key ->
                    val response = time_entry_id?.let {
                        timeEntriesUseCase.getTimeEntry(
                            api_key = api_key,
                            time_entry_id = time_entry_id
                        )
                    }
                    val statusResponse = response?.statusResponse
                    val data = response?.timeEntry
                    when (statusResponse) {
                        StatusResponse.OK -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Success,
                                    timeEntryInfo = data,
                                )
                            }
                        }

                        else -> {
                            mutableState.update {
                                it.copy(
                                    loadingState = LoadingState.Error
                                )
                            }
                        }
                    }
                }
            })
        })
    }

    override fun onNumberValueChange(value: String) {
        mutableState.update {
            it.copy(
                numberTask = value
            )
        }
    }

    override fun onActivityClick(id: Int) {
        mutableState.update {
            it.copy(
                activity_id = id
            )
        }
    }

    override fun onValueChangeComment(value: String) {
        mutableState.update {
            it.copy(
                comments = value
            )
        }
    }

    override fun chooseHours(value: Int) {
        mutableState.update {
            it.copy(
                hours = value
            )
        }
    }

    override fun chooseMinutes(value: Int) {
        mutableState.update {
            it.copy(
                minutes = value
            )
        }
    }

    override fun setTotalHours(value: Double) {
        mutableState.update {
            it.copy(
                hoursTotal = value
            )
        }
    }
}