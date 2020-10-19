package com.easycodingg.imagesearchapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easycodingg.imagesearchapp.R
import com.easycodingg.imagesearchapp.adapter.UnsplashLoadStateAdapter
import com.easycodingg.imagesearchapp.adapter.UnsplashPhotoAdapter
import com.easycodingg.imagesearchapp.data.UnsplashDiscoverPagingSource
import com.easycodingg.imagesearchapp.ui.viewmodels.MainViewModel
import com.easycodingg.imagesearchapp.utilities.Utilities.ORDER_BY_TYPE_LATEST
import com.easycodingg.imagesearchapp.utilities.Utilities.ORDER_BY_TYPE_OLDEST
import com.easycodingg.imagesearchapp.utilities.Utilities.ORDER_BY_TYPE_POPULAR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover.*

@AndroidEntryPoint
class DiscoverFragment: Fragment(R.layout.fragment_discover) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var photoAdapter: UnsplashPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()

        viewModel.discoverImages.observe(viewLifecycleOwner, Observer {
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        btnDiscoverRetry.setOnClickListener {
            photoAdapter.retry()
        }

    }

    private fun setupRecyclerView() {
        photoAdapter = UnsplashPhotoAdapter()
        rvDiscoverImages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashLoadStateAdapter() { photoAdapter.retry() },
                footer = UnsplashLoadStateAdapter() { photoAdapter.retry() }
            )
            itemAnimator = null
        }

        photoAdapter.setOnItemClickedListener {
            val action = DiscoverFragmentDirections.actionDiscoverFragmentToImageFragment(it)
            findNavController().navigate(action)
        }

        photoAdapter.addLoadStateListener { loadState->

            pbDiscover.isVisible = loadState.source.refresh is LoadState.Loading
            rvDiscoverImages.isVisible = loadState.source.refresh is LoadState.NotLoading
            btnDiscoverRetry.isVisible = loadState.source.refresh is LoadState.Error
            tvDiscoverError.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    photoAdapter.itemCount < 1) {
                rvDiscoverImages.isVisible = false
                tvDiscoverNoResults.isVisible = true
            } else {
                tvDiscoverNoResults.isVisible = false
            }
        }
    }

    private fun setupSpinner() {
        var spinnerTouched = false

        spSort.setSelection(getSpinnerSelection())

        spSort.setOnTouchListener { v, _ ->
            spinnerTouched = true
            v.performClick()
            false
        }

        spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(spinnerTouched){
                    when(position){
                        0 -> UnsplashDiscoverPagingSource.orderByType = ORDER_BY_TYPE_LATEST
                        1 -> UnsplashDiscoverPagingSource.orderByType = ORDER_BY_TYPE_POPULAR
                        2 -> UnsplashDiscoverPagingSource.orderByType = ORDER_BY_TYPE_OLDEST
                    }

                    photoAdapter.refresh()
                    spinnerTouched = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { /*NO_OP*/ }
        }
    }

    private fun getSpinnerSelection(): Int{
        return when(UnsplashDiscoverPagingSource.orderByType){
            ORDER_BY_TYPE_LATEST -> 0
            ORDER_BY_TYPE_POPULAR -> 1
            else -> 2
        }
    }
}