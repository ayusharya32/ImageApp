package com.easycodingg.imagesearchapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.easycodingg.imagesearchapp.R
import com.easycodingg.imagesearchapp.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment: Fragment(R.layout.fragment_image) {

    private val viewModel: MainViewModel by activityViewModels()
    private val args: ImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photo = args.UnsplashPhoto

        tvUsername.text = photo.user.name
        tvImageDescription.text = photo.description
        tvUserLocation.text = photo.user.location

        Glide.with(this)
            .load(photo.user.profile_image.large)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(ivProfileImage)

        Glide.with(this)
            .load(photo.urls.regular)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    pbImage.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    pbImage.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_error)
            .into(ivFullImage)

        floatingActionButton.setOnClickListener {
            viewModel.insertPhoto(photo)

            Snackbar.make(requireView(), "Added to Liked Images", Snackbar.LENGTH_SHORT)
                .setAction("Undo"){
                    viewModel.deletePhoto(photo)
                }
                .show()
        }

        tvUsername.setOnClickListener {
            Log.d("Profiley","Url: ${photo.user.links.html}")
            navigateToUserProfile(photo.user.links.html)
        }

        ivProfileImage.setOnClickListener{
            navigateToUserProfile(photo.user.links.html)
        }
    }

    private fun navigateToUserProfile(profileUrl: String) {
        val action = ImageFragmentDirections.actionImageFragmentToUserProfileFragment(profileUrl)
        findNavController().navigate(action)
    }
}