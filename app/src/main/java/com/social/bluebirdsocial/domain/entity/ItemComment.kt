package com.social.bluebirdsocial.domain.entity

data class ItemComment(
    val body: String? = "",
    val timestamp: Long? = 0,
    val idUser: String? = ""
) {
}