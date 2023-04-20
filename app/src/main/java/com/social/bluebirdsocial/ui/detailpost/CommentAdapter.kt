package com.social.bluebirdsocial.ui.detailpost

import android.view.View
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.ItemCommentBinding
import com.social.bluebirdsocial.databinding.ItemNotificationBinding
import com.social.bluebirdsocial.domain.entity.ItemComment
import com.social.bluebirdsocial.ui.BaseAdapter
import com.social.bluebirdsocial.ui.BaseRcvVH

class CommentAdapter: BaseAdapter() {

    inner class NotificationViewHolder(itemView: View): BaseRcvVH<ItemComment>(itemView){
        private val binding: ItemCommentBinding by lazy {
            ItemCommentBinding.bind(itemView)
        }

        override fun onBind(data: ItemComment) {
            with(binding){
                tvDetailUserComment.text = data.body
                tvHourUserComment.text = data.timestamp.toString()

            }
        }

    }

    override fun getLayoutResource(viewType: Int): Int = R.layout.item_comment

    override fun onCreateVH(itemView: View, viewType: Int): BaseRcvVH<ItemComment> {
        return NotificationViewHolder(itemView)
    }

}