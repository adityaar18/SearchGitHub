package com.aditya.searchgithub.data

data class DetailUserResponse(
    val login: String?,
    val name: String?,
    val avatar_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val followers: Int?,
    val following: Int?,
    val company: String?,
    val location: String?,
    val public_repos: Int = 0
)
