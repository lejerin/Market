package com.example.shopping.ui.detail

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.data.model.LoginRequest
import com.example.shopping.data.model.LoginResponse
import com.example.shopping.data.network.Output
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.data.repository.PurchaseRepository
import com.example.shopping.util.Coroutines
import kotlinx.coroutines.Job

class DetailViewModel(
    private val repository: PurchaseRepository
) : ViewModel() {

    private lateinit var job: Job


    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}