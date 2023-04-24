package com.social.bluebirdsocial.domain.entity

data class Post(
    val id: String? = "",
    val body: String? = "",
    val timestamp: Long? = 0,
    val idUser: String? = "",
    val likes: Int? = 0,
    val shares: Int? = 0,
    val comments: List<ItemComment>? = emptyList(),
    val urlImage: String? = ""
)