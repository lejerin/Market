package com.example.shopping.ui.search

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.data.network.Output
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.data.repository.SearchRepository
import com.example.shopping.util.Coroutines
import com.example.shopping.util.getActivity
import com.example.shopping.util.replaceFramgnet
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Job

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private lateinit var job: Job

    var search_name: String = ""
    var nextToken : String? = null

    private val _products = MutableLiveData<List<Product>>()
    val products : LiveData<List<Product>>
        get() = _products

    fun getSearchProductList(search_name: String){
        job = Coroutines.ioThenMain(
            {
                repository.searchProduct(search_name)
            },
            {
                when( it ){
                    is Output.Success -> {
                     _products.value = it.output.results
                    }
                    // do something with success result
                    is Output.Error -> System.out.println("오류")
                }
            }
        )

    }

    private val _isReset = MutableLiveData<Boolean>()
    val isReset : LiveData<Boolean>
        get() = _isReset

    fun startSearch(v : View){
        v.hideKeyboard()
        _isReset.value = true
        getSearchProductList(search_name)
    }

    val clicksListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    startSearch(v!!)
                }
                else -> return false
            }
            return true
        }

    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun fragmentFinish(v: View) {
        val activity = v.context.getActivity()
        activity?.onBackPressed()
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}