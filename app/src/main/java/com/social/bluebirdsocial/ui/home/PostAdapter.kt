package com.social.bluebirdsocial.ui.home

import android.view.View
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.ItemPostBinding
import com.social.bluebirdsocial.domain.entity.Post
import com.social.bluebirdsocial.ui.BaseAdapter
import com.social.bluebirdsocial.ui.BaseRcvVH

class PostAdapter: BaseAdapter() {
    override fun getLayoutResource(viewType: Int): Int = R.layout.item_post

    override fun onCreateVH(itemView: View, viewType: Int): BaseRcvVH<Post> {
        return PostViewHolder(itemView)
    }

    inner class PostViewHolder(itemView: View): BaseRcvVH<Post>(itemView) {
        val binding : ItemPostBinding by lazy {
            ItemPostBinding.bind(itemView)
        }

        override fun onBind(data: Post) {
            with(binding){

            }
        }

    }
}