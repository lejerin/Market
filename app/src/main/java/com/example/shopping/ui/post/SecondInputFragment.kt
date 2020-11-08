package com.example.shopping.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.shopping.R
import com.example.shopping.data.model.UploadRequest
import com.example.shopping.databinding.FragmentSecondInputBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SecondInputFragment : Fragment() {

    private lateinit var viewModel: PostViewModel
    private lateinit var binding: FragmentSecondInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_input, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as PostActivity).getViewModel()
        binding.postVM = viewModel

        viewModel.isUploadSuccess.observe(this, Observer {
            if(it){
                (activity as PostActivity).finish()
            }
        })

    }





}