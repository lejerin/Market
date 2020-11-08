package com.example.shopping.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.data.model.ProductResponse
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.databinding.FragmentHomeBinding
import com.example.shopping.ui.post.SecondInputFragment
import com.example.shopping.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    val searchFragment: SearchFragment = SearchFragment()

    private val products: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        rc_home.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //rc_home.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        rc_home.setHasFixedSize(true)
        rc_home.adapter = HomeAdapter(products)

        val api = ShopApi()
        val repository = ApiRepository(api)
        factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.homeVM = viewModel

        viewModel.getProductList()
        viewModel.products.observe(viewLifecycleOwner, Observer { list ->

            products.clear()
            products.addAll(list)
            rc_home.adapter!!.notifyDataSetChanged()

        })

        binding.goSearchBtn.setOnClickListener {
            viewModel.replaceFragment(this, searchFragment, true, it)
        }

    }


}