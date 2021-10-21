package com.ayeshaazeema.githubapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayeshaazeema.githubapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowViewModel : ViewModel() {

    val listFollow = MutableLiveData<ArrayList<User>>()

    fun setListFollow(username: String, page: String, context: Context) {
        val listUser = ArrayList<User>()

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
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        val user = User()
                        user.username = jsonObject.getString("login")
                        user.avatar = jsonObject.getString("avatar_url")

                        listUser.add(user)
                    }

                    listFollow.postValue(listUser)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getListFollow(): LiveData<ArrayList<User>> {
        return listFollow
    }

}