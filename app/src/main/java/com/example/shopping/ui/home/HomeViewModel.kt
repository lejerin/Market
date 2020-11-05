package com.example.shopping.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.data.model.Product
import com.example.shopping.data.network.Output
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.util.Coroutines
import kotlinx.coroutines.Job

class HomeViewModel(
    private val repository: ApiRepository
) : ViewModel() {

    private lateinit var job: Job

    private val _products = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>>
        get() = _products

    fun getProductList(){
        job = Coroutines.ioThenMain(
            { repository.getProduct() },
            {
                when( it ){
                    is Output.Success -> _products.value = it.output.results
                    // do something with success result
                    is Output.Error -> System.out.println("오류")
                }
            }
        )

    }




    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}