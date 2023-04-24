package com.social.bluebirdsocial.ui.home

import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.social.bluebirdsocial.R
import com.social.bluebirdsocial.databinding.ItemPostBinding
import com.social.bluebirdsocial.domain.entity.ItemComment
import com.social.bluebirdsocial.domain.entity.Post
import com.social.bluebirdsocial.domain.entity.User
import com.social.bluebirdsocial.ui.BaseAdapter
import com.social.bluebirdsocial.ui.BaseRcvVH
import com.social.bluebirdsocial.ui.custom.toDateString
import java.util.Locale

class PostAdapter: BaseAdapter() {
    var onClickComment: ((String) -> Unit)? = null
    var onClickLike: ((String, Int) -> Unit)? = null
    var listUser: ArrayList<User>? = ArrayList()

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
                val user = data.idUser?.let { getUserData(it) }
                tvItemNotificationContent.text = data.body
                tvItemPostLike.text = data.likes.toString()
                tvItemPostShare.text = data.shares.toString()
                tvItemPostComment.text = data.comments?.size.toString() ?: "0"
                tvPostItemHour.text = data.timestamp?.toDateString() ?: "0"
                if (user != null && !user.idUser.isNullOrBlank()){
                    tvPostItemName.text = user.name
                    tvPostItemUser.text = user.email
                }

                tvItemPostComment.setOnClickListener {
                    data.id?.let { it1 -> onClickComment?.invoke(it1) }
                }
                tvItemPostLike.setOnClickListener {
                    data.id?.let { it1 -> data.likes?.let { it2 -> onClickLike?.invoke(it1, it2) } }
                }
                tvItemPostShare.setOnClickListener {

                }
            }
        }

        private fun getUserData(id: String): User {
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
}