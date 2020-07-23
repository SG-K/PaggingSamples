package com.example.paggingsample.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.paggingsample.categories.models.CategoriesResponse
import com.example.paggingsample.categories.models.ItemsItem
import com.example.paggingsample.categories.print
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class PaggingSampleRepo(private val paggingSampleAPi: PaggingSampleAPi) {


     suspend fun getCategories(liveData : MutableLiveData<CustomResult<CategoriesResponse>>, query : String){
        liveData.postValue(CustomResult.Loading(true))
        try{
            val response = paggingSampleAPi.getCategories("IN","3","0")
            liveData.postValue(CustomResult.Success(response))
        }catch (e : java.lang.Exception){
            if (e.message?.isNotEmpty() == true) {
                liveData.postValue(CustomResult.Error.RecoverableError(e))
            }else{
                liveData.postValue(CustomResult.Error.NonRecoverableError(Exception("Un-traceable")))
            }
        }
//        paggingSampleAPi.getCategories()
//            .enqueue(object : Callback<CategoriesResponse> {
//                override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
//                    Log.e("onFailure", t.message.toString())
//                    if (t.message != null && t.message?.isNotEmpty() == true){
//                        liveData.value = CustomResult.Error.RecoverableError(Exception(t.message.toString()))
//                    }else{
//                        liveData.value = CustomResult.Error.NonRecoverableError(Exception("Un-traceable"))
//                    }
//                }
//
//                override fun onResponse(
//                    call: Call<CategoriesResponse>,
//                    response: Response<CategoriesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        liveData.value = CustomResult.Success(response.body()!!)
//                    } else {
//                        "got into onResponse fail ${response.code()}".print()
//                        if (response.message().isNotEmpty()) {
//                            liveData.value = CustomResult.Error.RecoverableError(Exception(response.message()))
//                        }else{
//                            liveData.value = CustomResult.Error.NonRecoverableError(Exception("Un-traceable"))
//                        }
//                    }
//                }
//            })
    }


    fun getPaggingCategories():
            Flow<PagingData<ItemsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(paggingSampleAPi) }
        ).flow
    }

}

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 3

class GithubPagingSource(
    private val paggingSampleAPi: PaggingSampleAPi
) : PagingSource<Int, ItemsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsItem> {
        "got into load".print()
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
//        val apiQuery = query + IN_QUALIFIER
        return try {
            val response = paggingSampleAPi.getCategories("IN","$position","${position-3}")
            val repos = response.categories?.items?:ArrayList()
            "got the result in pagging surce with size ${repos.size}".print()
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 3,
                nextKey = if (repos.isEmpty()) null else position + 3
            )
        } catch (exception: IOException) {
            "got into execption 1 ${exception.message}".print()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            "got into execption 2 ${exception.message}".print()
            return LoadResult.Error(exception)
        }
    }
}