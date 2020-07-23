package com.example.paggingsample.categories

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.paggingsample.R
import com.example.paggingsample.network.CustomResult
import org.koin.android.viewmodel.ext.android.viewModel

class CategoriesActivity : AppCompatActivity() {

    private val viewmodelGitHuSearch : CategoriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodelConnections()
        viewmodelGitHuSearch.getReposFromGitHub()
    }


    private fun viewmodelConnections() {
        viewmodelGitHuSearch.observeData().observe(this, Observer {
            when(it){
                is CustomResult.Success -> {
                   "got inot Sucess ${ it.data}".print()
                }
                is CustomResult.Error.RecoverableError -> {
                    "got inot RecoverableError ${ it.exception}".print()
                }
                is CustomResult.Error.NonRecoverableError -> {
                    "got inot NonRecoverableError ${ it.exception}".print()
                }
                is CustomResult.Loading -> {
                    "got inot Loading ${ it.status}".print()
                }
            }
        })
    }

}

fun Any.print(){
    Log.v("PagginggsAmpe ", " $this")
}