package com.ayeshaazeema.githubapp.model

data class User(
    var username: String? = null,
    var name: String? = null,
    var location: String? = null,
    var followers: Int? = 0,
    var following: Int? = 0,
    var avatar: String? = null
)