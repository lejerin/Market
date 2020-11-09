package com.example.shopping.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.data.repository.SearchRepository

class SearchViewModelFactory(
    private val repository: SearchRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }

}