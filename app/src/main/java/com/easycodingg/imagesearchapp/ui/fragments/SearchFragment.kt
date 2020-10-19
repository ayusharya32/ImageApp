package com.easycodingg.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.easycodingg.imagesearchapp.R
import com.easycodingg.imagesearchapp.adapter.UnsplashLoadStateAdapter
import com.easycodingg.imagesearchapp.adapter.UnsplashPhotoAdapter
import com.easycodingg.imagesearchapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment: Fragment(R.layout.fragment_search) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var photoAdapter: UnsplashPhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.searchImages.observe(viewLifecycleOwner, Observer {
            photoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        btnSearchRetry.setOnClickListener {
            photoAdapter.retry()
        }
    }

    private fun setupRecyclerView() {
        photoAdapter = UnsplashPhotoAdapter()
        rvSearchImages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashLoadStateAdapter() { photoAdapter.retry() },
                footer = UnsplashLoadStateAdapter() { photoAdapter.retry() }
            )
            itemAnimator = null
        }

        photoAdapter.setOnItemClickedListener {
            val action = SearchFragmentDirections.actionSearchFragmentToImageFragment(it)
            findNavController().navigate(action)
        }

        photoAdapter.addLoadStateListener { loadState->

            pbSearch.isVisible = loadState.source.refresh is LoadState.Loading
            rvSearchImages.isVisible = loadState.source.refresh is LoadState.NotLoading
            btnSearchRetry.isVisible = loadState.source.refresh is LoadState.Error
            tvSearchError.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                photoAdapter.itemCount < 1) {
                rvSearchImages.isVisible = false
                tvSearchNoResults.isVisible = true
            } else {
                tvSearchNoResults.isVisible = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.miActionSearch)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search Images.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    rvSearchImages.scrollToPosition(0)
                    viewModel.getSearchImages(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}