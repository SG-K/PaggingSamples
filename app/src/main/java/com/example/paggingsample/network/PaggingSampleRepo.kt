package com.example.paggingsample.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.paggingsample.categories.models.CategoriesResponse
import com.example.paggingsample.categories.print
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaggingSampleRepo(private val paggingSampleAPi: PaggingSampleAPi) {


     fun getCategories(liveData : MutableLiveData<CustomResult<CategoriesResponse>>, query : String){
        liveData.value = CustomResult.Loading(true)
        paggingSampleAPi.getCategories()
            .enqueue(object : Callback<CategoriesResponse> {
                override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                    if (t.message != null && t.message?.isNotEmpty() == true){
                        liveData.value = CustomResult.Error.RecoverableError(Exception(t.message.toString()))
                    }else{
                        liveData.value = CustomResult.Error.NonRecoverableError(Exception("Un-traceable"))
                    }
                }

                override fun onResponse(
                    call: Call<CategoriesResponse>,
                    response: Response<CategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        liveData.value = CustomResult.Success(response.body()!!)
                    } else {
                        "got into onResponse fail ${response.code()}".print()
                        if (response.message().isNotEmpty()) {
                            liveData.value = CustomResult.Error.RecoverableError(Exception(response.message()))
                        }else{
                            liveData.value = CustomResult.Error.NonRecoverableError(Exception("Un-traceable"))
                        }
                    }
                }
            })
    }


}