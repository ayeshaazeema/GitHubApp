package com.ayeshaazeema.githubapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayeshaazeema.githubapp.model.Users
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

class FollowViewModel : ViewModel() {

    val listFollow = MutableLiveData<ArrayList<Users>>()

    fun setListFollow(username: String, page: String, context: Context) {

        val client = AsyncHttpClient()

        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        val url = when (page) {
            "followers" -> "https://api.github.com/users/$username/followers"
            "following" -> "https://api.github.com/users/$username/following"
            else -> ""
        }

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}