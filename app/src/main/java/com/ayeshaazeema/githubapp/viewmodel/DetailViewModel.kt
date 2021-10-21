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
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val detailUser = MutableLiveData<User>()

    fun setDetailUser(username: String, context: Context) {

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$username"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)

                    val user = User()
                    user.name = jsonObject.getString("name")
                    user.username = jsonObject.getString("login")
                    user.location = jsonObject.getString("location")
                    user.followers = jsonObject.getInt("followers")
                    user.following = jsonObject.getInt("following")
                    user.avatar = jsonObject.getString("avatar_url")

                    detailUser.postValue(user)
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

    fun getDetailUser(): LiveData<User> {
        return detailUser
    }
}