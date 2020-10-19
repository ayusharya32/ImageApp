package com.easycodingg.imagesearchapp.ui.fragments

import android.content.ClipData
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easycodingg.imagesearchapp.R
import com.easycodingg.imagesearchapp.adapter.LikedPhotosAdapter
import com.easycodingg.imagesearchapp.adapter.UnsplashLoadStateAdapter
import com.easycodingg.imagesearchapp.adapter.UnsplashPhotoAdapter
import com.easycodingg.imagesearchapp.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.fragment_liked.*

class LikedFragment: Fragment(R.layout.fragment_liked) {

    private val viewModel: MainViewModel by activityViewModels()
    lateinit var photoAdapter: LikedPhotosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setSwipeToDelete()

        viewModel.getAllPhotos().observe(viewLifecycleOwner, Observer {
            photoAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        photoAdapter = LikedPhotosAdapter()
        rvLikedImages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
            itemAnimator = null
        }

        photoAdapter.setOnItemClickedListener {
            val action = LikedFragmentDirections.actionLikedFragmentToImageFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun setSwipeToDelete() {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val photo = photoAdapter.currentList[position]

                viewModel.deletePhoto(photo)

                Snackbar.make(requireView(), "Image Removed", Snackbar.LENGTH_SHORT)
                    .setAction("Undo"){
                        viewModel.insertPhoto(photo)
                    }
                    .show()
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(rvLikedImages)
    }
}