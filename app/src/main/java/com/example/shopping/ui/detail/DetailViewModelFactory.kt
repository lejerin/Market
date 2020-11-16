package com.example.shopping.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.data.repository.PurchaseRepository
import com.example.shopping.ui.login.LoginViewModel

class DetailViewModelFactory(
    private val repository: PurchaseRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }

}