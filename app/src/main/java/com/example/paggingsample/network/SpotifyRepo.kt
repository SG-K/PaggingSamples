package com.example.paggingsample.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.paggingsample.categories.models.CategoriesResponse
import com.example.paggingsample.categories.models.CategoryItem
import com.example.paggingsample.categories.print
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class SpotifyRepo(private val spotifyAPI: SpotifyAPI) {


     suspend fun getCategories(liveData : MutableLiveData<CustomResult<CategoriesResponse>>, query : String){
        liveData.postValue(CustomResult.Loading(true))
        try{
            val response = spotifyAPI.getCategories("IN","3","0")
            liveData.postValue(CustomResult.Success(response))
        }catch (e : java.lang.Exception){
            if (e.message?.isNotEmpty() == true) {
                liveData.postValue(CustomResult.Error.RecoverableError(e))
            }else{
                liveData.postValue(CustomResult.Error.NonRecoverableError(Exception("Un-traceable")))
            }
        }
    }


    fun getPaggingCategories():
            Flow<PagingData<CategoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = loadSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SpotifyPagingSource(spotifyAPI) }
        ).flow
    }

}

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val loadSize = 5
private val INTIAL_SPOTIFY_PAGE_INDEX = SptifyPagingIndex(limit = loadSize,offest = 0,total = 0)

class SpotifyPagingSource(
    private val spotifyAPI: SpotifyAPI
) : PagingSource<SptifyPagingIndex, CategoryItem>() {

    override suspend fun load(params: LoadParams<SptifyPagingIndex>): LoadResult<SptifyPagingIndex, CategoryItem> {
        "got into load".print()
        val limit = params.key?.limit ?: INTIAL_SPOTIFY_PAGE_INDEX.limit
        val offest = params.key?.offest ?: INTIAL_SPOTIFY_PAGE_INDEX.offest
        return try {
            "tackinglimits limit $limit ".print()
            "tackinglimits offest ${offest} ".print()
            val response = spotifyAPI.getCategories("IN","$limit","$offest")
            val categories = response.categories?.items?:ArrayList()
            "got the result in tackinglimits with size ${categories.size}".print()

            val (previousKey, nextKey) = paggingIndexing(offest = offest,
                totalFromServer = response.categories?.total?:0,
                totalFromPagging = params.key?.total?:0)

            LoadResult.Page(
                data = categories,
                prevKey = previousKey,
                nextKey =nextKey)
        } catch (exception: IOException) {
            "got into execption 1 ${exception.message}".print()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            "got into execption 2 ${exception.message}".print()
            return LoadResult.Error(exception)
        }
    }

    fun paggingIndexing(offest : Int, totalFromServer : Int,
                        totalFromPagging : Int  ): Pair<SptifyPagingIndex?,SptifyPagingIndex?> {
        var nextKeyOffest : Int? = offest + loadSize
        if (offest != 0 && offest >= totalFromPagging){
            nextKeyOffest = null
        }
        var previousKeyOffest : Int? = offest
        if (offest == 0){
            previousKeyOffest = null
        }

        val previousKey = previousKeyOffest?.let {
            SptifyPagingIndex(limit= loadSize,offest = it ,total = totalFromServer)
        }

        val nextKey = nextKeyOffest?.let {
            SptifyPagingIndex(limit= loadSize,offest = it,total = totalFromServer)
        }

        return Pair(previousKey, nextKey)

    }

}


data class SptifyPagingIndex(val limit : Int, val offest : Int, val total : Int)