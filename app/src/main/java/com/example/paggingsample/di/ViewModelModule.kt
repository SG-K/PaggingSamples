package com.example.paggingsample.di

import com.example.paggingsample.categories.CategoriesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { CategoriesViewModel(get()) }
}