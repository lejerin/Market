package com.example.shopping.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.R
import com.example.shopping.data.model.Product
import com.example.shopping.data.network.ShopApi
import com.example.shopping.data.repository.ApiRepository
import com.example.shopping.databinding.FragmentHomeBinding
import com.example.shopping.ui.detail.DetailActivity
import com.example.shopping.ui.search.SearchFragment
import com.example.shopping.util.RecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() ,
    RecyclerViewClickListener {

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


        rc_home.layoutManager = GridLayoutManager(context, 2)
        //rc_home.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //rc_home.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        rc_home.setHasFixedSize(true)
        rc_home.adapter = HomeAdapter(products, this)

        val api = ShopApi()
        val repository = ApiRepository(api)
        factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.homeVM = viewModel

        viewModel.getProductList()
        viewModel.products.observe(viewLifecycleOwner, Observer { list ->

            val initialSize: Int = products.size
            products.addAll(list)
            rc_home.adapter!!.notifyItemRangeInserted(initialSize, products.size-1)
        })

        binding.goSearchBtn.setOnClickListener {
            viewModel.replaceFragment(this, searchFragment, true, it)
        }

        addScrollerListener()

    }


    private var lastVisibleItemPosition = 0
    private fun addScrollerListener()
    {
        rc_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                if (lastVisibleItemPosition === itemTotalCount) {
                    //비디오 추가 갱신할 때
                    viewModel.getProductList()
                }
            }
        })
    }

    override fun onRecyclerViewItemClick(view: View, pos: Int) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", products[pos].id)
        startActivity(intent)
    }

}