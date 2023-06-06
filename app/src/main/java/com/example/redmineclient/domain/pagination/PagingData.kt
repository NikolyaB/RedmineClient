package com.example.redmineclient.domain.pagination

import com.example.redmineclient.domain.state.LoadingState

interface Identifier {
    val id: Int
}

data class PagingData<T : Identifier>(
    var loadingState: LoadingState = LoadingState.Loading,
    var isRefreshing: Boolean = false,
    var isAppending: Boolean = false,

    val title: String? = null,
    val data: MutableList<T> = mutableListOf()
)