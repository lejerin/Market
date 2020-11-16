package com.example.shopping.ui.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.data.network.Output
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.util.Coroutines
import com.example.shopping.util.replaceFramgnet
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Job

class HomeViewModel(
    private val repository: ApiRepository
) : ViewModel() {

    private lateinit var job: Job

    private val _products = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>>
        get() = _products


    var page: Int? = 1

    fun getProductList(){
        if(page != null){
            job = Coroutines.ioThenMain(
                { repository.getProduct(page!!) },
                {
                    when( it ){
                        is Output.Success -> {
                            page = if(it.output.next != null){
                                page!! + 1
                            }else{
                                null
                            }
                            _products.value = it.output.results
                        }
                        // do something with success result
                        is Output.Error -> System.out.println("오류")
                    }
                }
            )
        }
    }


    fun replaceFragment(from: Fragment, to: Fragment, addToBackStack: Boolean, v: View){
        v.context.replaceFramgnet(from, to, addToBackStack, R.id.main_frame, true)
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}