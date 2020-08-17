package com.example.paggingsample.categories

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.map
import com.example.paggingsample.JlGlideDeligate
import com.example.paggingsample.R
import com.example.paggingsample.categories.adapter.CategoriesAdapter
import com.example.paggingsample.categories.models.UIModel
import com.example.paggingsample.network.CustomResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class CategoriesActivity : AppCompatActivity() {

    private val categoriesViewModel : CategoriesViewModel by viewModel()
    lateinit var adapter: CategoriesAdapter
    lateinit var glideDeligate: JlGlideDeligate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        glideDeligate = JlGlideDeligate(this)
        adapter = CategoriesAdapter(glideDeligate)
        categories_recyclerView?.adapter = adapter

        categoriesViewModel.scope.launch {
            categoriesViewModel.searchRepo().collectLatest {
                adapter.submitData(it)
            }

        }
    }

}
fun Any.print(){
    Log.v("PagginggsAmpe ", " $this")
}