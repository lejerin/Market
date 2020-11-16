package com.example.shopping.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.shopping.R
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.PurchaseRepository
import com.example.shopping.data.repository.UploadPostRepository
import com.example.shopping.databinding.ActivityDetailBinding
import com.example.shopping.databinding.ActivityPostBinding
import com.example.shopping.ui.post.PostViewModel
import com.example.shopping.ui.post.PostViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var factory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewDataBinding: ActivityDetailBinding

    private val TAG = "PostActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)

        //liveData를 사용하기 위해 if not 관찰해도 refresh가 되지않음
        viewDataBinding.lifecycleOwner = this

        val api = ShopApi()
        val repository = PurchaseRepository(api)
        factory = DetailViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)


    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

}