package com.easycodingg.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingg.imagesearchapp.R
import kotlinx.android.synthetic.main.unsplash_photo_load_state_footer.view.*

class UnsplashLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<UnsplashLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unsplash_photo_load_state_footer, parent, false)
        return LoadStateViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.itemView.apply {
            pbFooter.isVisible = loadState is LoadState.Loading
            tvFooterError.isVisible = loadState !is LoadState.Loading
            btnFooterRetry.isVisible = loadState !is LoadState.Loading
        }

        holder.itemView.btnFooterRetry.setOnClickListener {
            retry.invoke()
        }
    }

}