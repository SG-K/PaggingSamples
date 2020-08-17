package com.example.paggingsample.categories.models

sealed class UIModel {

    class CategoryModel(val item : CategoryItem) : UIModel()

    class SeparatorModel(val description: String) : UIModel()

    object Header : UIModel()

    object Footer : UIModel()

}