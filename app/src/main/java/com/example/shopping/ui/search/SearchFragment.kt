package com.example.shopping.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.data.repository.SearchRepository
import com.example.shopping.databinding.FragmentHomeBinding
import com.example.shopping.databinding.FragmentSearchBinding
import com.example.shopping.ui.detail.DetailActivity
import com.example.shopping.ui.home.HomeAdapter
import com.example.shopping.ui.home.HomeViewModel
import com.example.shopping.ui.home.HomeViewModelFactory
import com.example.shopping.util.RecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() ,
    RecyclerViewClickListener {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var factory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    private val products: MutableList<Product> = mutableListOf()

    private var startNum = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRc()

        val api = ShopApi()
        val repository = SearchRepository(api)
        factory = SearchViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        binding.searchVM = viewModel

        viewModel.products.observe(this, Observer {
            updateRc(it)
        })

        viewModel.isReset.observe(this, Observer {
            if(it){
                products.clear()
                startNum = 0
                rc_search.adapter!!.notifyDataSetChanged()
            }
        })

    }

    private fun initRc(){
        rc_search.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_search.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rc_search.setHasFixedSize(true)
        rc_search.adapter = SearchAdapter(products, this)
    }

    private fun updateRc(list: List<Product>){
        (rc_search.adapter!! as SearchAdapter).setIsNext(viewModel.nextToken)
        products.addAll(list)
        rc_search.adapter!!.notifyItemRangeInserted(startNum,products.size-1)
        startNum = products.size
    }

    private fun finish(){
        this.finish()
    }

    override fun onRecyclerViewItemClick(view: View, pos: Int) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", products[pos].id)
        startActivity(intent)
    }

}