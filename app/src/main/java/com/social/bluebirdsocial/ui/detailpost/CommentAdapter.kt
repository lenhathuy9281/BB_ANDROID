package com.social.bluebirdsocial.ui.detailpost

import android.view.View
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.ItemCommentBinding
import com.social.bluebirdsocial.databinding.ItemNotificationBinding
import com.social.bluebirdsocial.domain.entity.ItemComment
import com.social.bluebirdsocial.domain.entity.User
import com.social.bluebirdsocial.ui.BaseAdapter
import com.social.bluebirdsocial.ui.BaseRcvVH
import com.social.bluebirdsocial.ui.custom.toDateString

class CommentAdapter: BaseAdapter() {

    var listUser: ArrayList<User>? = ArrayList()

    inner class NotificationViewHolder(itemView: View): BaseRcvVH<ItemComment>(itemView){
        private val binding: ItemCommentBinding by lazy {
            ItemCommentBinding.bind(itemView)
        }

        override fun onBind(data: ItemComment) {
            with(binding){
                tvDetailUserComment.text = data.body
                tvHourUserComment.text = data.timestamp?.toDateString() ?: "0"
                val user = data.idUser?.let { getUserData(it) }
                if (user != null && !user.name.isNullOrBlank()) {
                    tvNameUserComment.text = user.name
                    tvMailUserComment.text = user.email
                }

            }
        }

    }

    override fun getLayoutResource(viewType: Int): Int = R.layout.item_comment

    override fun onCreateVH(itemView: View, viewType: Int): BaseRcvVH<ItemComment> {
        return NotificationViewHolder(itemView)
    }

    fun getUserData(id: String): User {
        if (listUser!= null && listUser!!.isNotEmpty()) {
            for (user in listUser!!) {
                if (user.idUser.equals(id)){
                    return user
                }
            }
        }
        return User()
    }

}