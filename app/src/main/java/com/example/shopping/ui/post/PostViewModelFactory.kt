package com.example.shopping.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopping.data.repository.UploadPostRepository


@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(
    private val repository: UploadPostRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }

}