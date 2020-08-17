package com.example.paggingsample.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.paggingsample.categories.models.CategoriesResponse
import com.example.paggingsample.categories.models.CategoryItem
import com.example.paggingsample.categories.models.UIModel
import com.example.paggingsample.network.CustomResult
import com.example.paggingsample.network.SpotifyRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesViewModel(val spotifyRepo: SpotifyRepo) : ViewModel() {

    val job = Job()
    val scope = CoroutineScope(job + Dispatchers.IO)
    private var currentSearchResult: Flow<PagingData<UIModel>>? = null

    fun searchRepo(): Flow<PagingData<UIModel>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            "halted in viewmodel ".print()
            return lastResult
        }
        "executing in viewmodel ".print()
        val newResult: Flow<PagingData<UIModel>> = spotifyRepo.getPaggingCategories()
            .cachedIn(scope)
        currentSearchResult = newResult
        return newResult
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }

}