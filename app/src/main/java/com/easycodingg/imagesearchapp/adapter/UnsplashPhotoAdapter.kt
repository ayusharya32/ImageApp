package com.easycodingg.imagesearchapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.easycodingg.imagesearchapp.R
import com.easycodingg.imagesearchapp.model.photoobject.UnsplashPhoto
import kotlinx.android.synthetic.main.image_item.view.*

class UnsplashPhotoAdapter: PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(differCallback){

    inner class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object{
        private val differCallback = object :DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        val curPhoto = getItem(position)

        holder.itemView.apply {
            Glide.with(this)
                .load(curPhoto?.urls?.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(ivImageItem)
            tvItemUsername.text = curPhoto?.user?.username
        }

        holder.itemView.setOnClickListener {
            onItemClickedListener?.let {
                it(getItem(position)!!)
            }
        }
    }

    private var onItemClickedListener: ((UnsplashPhoto) -> Unit)? = null

    fun setOnItemClickedListener(listener: (UnsplashPhoto) -> Unit) {
        onItemClickedListener = listener
    }
}