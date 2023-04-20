package com.social.bluebirdsocial.ui.notifications

import android.view.View
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.ItemNotificationBinding
import com.social.bluebirdsocial.domain.entity.ItemNotification
import com.social.bluebirdsocial.ui.BaseAdapter
import com.social.bluebirdsocial.ui.BaseRcvVH

class NotificationAdapter: BaseAdapter() {
    var onClickNotification: (() -> Unit)? = null

    inner class NotificationViewHolder(itemView: View): BaseRcvVH<ItemNotification>(itemView){
        private val binding: ItemNotificationBinding by lazy {
            ItemNotificationBinding.bind(itemView)
        }

        override fun onBind(data: ItemNotification) {
            with(binding){

                tvItemNotificationContent.text = data.body

                root.setOnClickListener {
                    onClickNotification?.invoke()
                }
            }
        }

    }

    override fun getLayoutResource(viewType: Int): Int = R.layout.item_notification

    override fun onCreateVH(itemView: View, viewType: Int): BaseRcvVH<ItemNotification> {
        return NotificationViewHolder(itemView)
    }

}