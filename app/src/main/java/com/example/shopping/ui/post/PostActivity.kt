package com.example.shopping.ui.post

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shopping.R
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.UploadPostRepository
import com.example.shopping.databinding.ActivityPostBinding
import com.example.shopping.ui.home.HomeFragment
import com.example.shopping.util.CameraUtil
import kotlinx.android.synthetic.main.activity_login.*


class PostActivity : AppCompatActivity() {

    private lateinit var factory: PostViewModelFactory
    private lateinit var viewModel: PostViewModel
    private lateinit var viewDataBinding: ActivityPostBinding

    private val TAG = "PostActivity"

    //사진 찍기
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_PICK = 10

    private val PERMISSION_REQUEST_CODE = 100
    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val firstInputFragment: FirstInputFragment =
        FirstInputFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<ActivityPostBinding>(this, R.layout.activity_post)

        //liveData를 사용하기 위해 if not 관찰해도 refresh가 되지않음
        viewDataBinding.lifecycleOwner = this

        setFragment(firstInputFragment)

        val api = ShopApi()
        val repository = UploadPostRepository(api)
        factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)


    }

    fun getViewModel(): PostViewModel = viewModel


    private fun setFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_post_frame, fragment)
        ft.commit()
    }

    lateinit var clickImg: ImageView

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //사진찍은거 파일로 저장하고 가져오기
            firstInputFragment.onActivityResult(requestCode, resultCode, data)


        }

        //사진을 갤러리에서 갖고왔을 때
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_PICK && data != null) {
            firstInputFragment.onActivityResult(requestCode, resultCode, data)

        }
    }


    override fun finish() {
        super.finish()

      //  overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }





}
