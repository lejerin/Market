package com.example.shopping.ui.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.data.model.*
import com.example.shopping.data.network.Output
import com.example.shopping.data.repository.PurchaseRepository
import com.example.shopping.util.Coroutines
import com.example.shopping.util.getActivity
import kotlinx.coroutines.Job

class DetailViewModel(
    private val repository: PurchaseRepository
) : ViewModel() {

    private lateinit var job: Job


    private val _product = MutableLiveData<Product>()
    val product : LiveData<Product>
        get() = _product


    fun getProductWithId(id: Int){
        job = Coroutines.ioThenMain(
            { repository.getProductWithId(id) },
            {
                when( it ){
                    is Output.Success -> {
                        _product.value = it.output
                    }
                    // do something with success result
                    is Output.Error -> println(it.exception.message)
                }
            }
        )
    }

    private val _isOk = MutableLiveData<PurchaseResponse>()
    val isOk : LiveData<PurchaseResponse>
        get() = _isOk

    lateinit var UID: String
    var PID = 0
    var stock = ""
    var mark = 0

    fun purchaseProduct(){

        println("구입" + UID + " " + PID)
        println(Integer.parseInt(stock))
            job = Coroutines.ioThenMain(
                { repository.purchaseProduct(PurchaseRequest(UID, PID.toString(), Integer.parseInt(stock), mark)) },
                {
                    when( it ){
                        is Output.Success -> {
                           _isOk.value = it.output
                        }
                        // do something with success result
                        is Output.Error -> println(it.exception.message)
                    }
                }
            )

    }

    fun activityFinish(v: View) {
        val activity = v.context.getActivity()
        activity?.finish()
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}