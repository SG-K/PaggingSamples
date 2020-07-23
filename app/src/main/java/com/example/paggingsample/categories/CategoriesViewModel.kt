package com.example.paggingsample.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paggingsample.categories.models.CategoriesResponse
import com.example.paggingsample.categories.models.ItemsItem
import com.example.paggingsample.network.CustomResult
import com.example.paggingsample.network.GithubPagingSource
import com.example.paggingsample.network.PaggingSampleRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class CategoriesViewModel(val paggingSampleRepo: PaggingSampleRepo) : ViewModel() {

    private val githubRepoLiveData = MutableLiveData<CustomResult<CategoriesResponse>>()
    val job = Job()
    val scope = CoroutineScope(job + Dispatchers.IO)

    fun observeData() : MutableLiveData<CustomResult<CategoriesResponse>> {
        return githubRepoLiveData
    }

    fun getReposFromGitHub(){
        scope.launch {
            paggingSampleRepo.getCategories(liveData = githubRepoLiveData, query = "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancelChildren()
    }


    private var currentSearchResult: Flow<PagingData<ItemsItem>>? = null
    fun searchRepo(): Flow<PagingData<ItemsItem>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            "halted in viewmodel ".print()
            return lastResult
        }
        "executing in viewmodel ".print()
        val newResult: Flow<PagingData<ItemsItem>> = paggingSampleRepo.getPaggingCategories()
            .cachedIn(scope)
        currentSearchResult = newResult
        return newResult
    }

}