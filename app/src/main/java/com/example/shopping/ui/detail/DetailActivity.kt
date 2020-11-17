package com.example.shopping.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.PurchaseRepository
import com.example.shopping.data.repository.UploadPostRepository
import com.example.shopping.databinding.ActivityDetailBinding
import com.example.shopping.databinding.ActivityPostBinding
import com.example.shopping.ui.post.PostViewModel
import com.example.shopping.ui.post.PostViewModelFactory
import com.example.shopping.util.PreferenceUtil

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

        val id = intent.getIntExtra("id", 0)
        viewModel.getProductWithId(id)
        viewModel.product.observe(this, Observer {
            viewDataBinding.product = it
        })


        viewDataBinding.detailVM = viewModel

        viewModel.UID = PreferenceUtil(this).getUID().toString()
        viewModel.PID = id

        viewModel.isOk.observe(this, Observer {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            finish()
        })



        viewDataBinding.ratingBar.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                viewModel.mark = p1.toInt()
            }
        })


    }

    override fun finish() {
        super.finish()


    }

}