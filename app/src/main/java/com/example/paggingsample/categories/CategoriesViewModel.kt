package com.example.paggingsample.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paggingsample.categories.models.CategoriesResponse
import com.example.paggingsample.network.CustomResult
import com.example.paggingsample.network.PaggingSampleRepo

class CategoriesViewModel(val paggingSampleRepo: PaggingSampleRepo) : ViewModel() {

    private val githubRepoLiveData = MutableLiveData<CustomResult<CategoriesResponse>>()

    fun observeData() : MutableLiveData<CustomResult<CategoriesResponse>> {
        return githubRepoLiveData
    }

    fun getReposFromGitHub(){
        paggingSampleRepo.getCategories(liveData = githubRepoLiveData, query = "")
    }


}