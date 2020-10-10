package com.example.shopping.ui.post

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import com.example.shopping.R
import com.example.shopping.databinding.FragmentFirstInputBinding
import com.example.shopping.util.CameraUtil

class FirstInputFragment : Fragment() {

    private lateinit var viewModel: PostViewModel
    private lateinit var binding: FragmentFirstInputBinding

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_PICK = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_input, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as PostActivity).getViewModel()
        binding.postVM = viewModel


        binding.btnNext.setOnClickListener {
            viewModel.replaceFragment(this, SecondInputFragment(), true, it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //사진찍은거 파일로 저장하고 가져오기
            val uri = CameraUtil.getInstance(context!!).makeBitmap(binding.addImg)
            CameraUtil.getInstance(context!!).setImageView(binding.addImg, uri)

            viewModel.product_img = uri.toFile()
        }

        //사진을 갖고왔을 때
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_PICK && data != null) {
            val uri = data.data!!
            CameraUtil.getInstance(context!!).setImageView(binding.addImg, uri)

            viewModel.product_img = uri.toFile()
        }

    }

}