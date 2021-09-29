package com.ayeshaazeema.githubapp.model

data class User(
    var username: String? = null,
    var name: String? = "",
    var location: String? = "",
    var follower: Int? = 0,
    var following: Int? = 0,
    var avatar: String? = null
)