package com.ayeshaazeema.githubapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.activity.DetailActivity
import com.ayeshaazeema.githubapp.adapter.FollowingAdapter
import com.ayeshaazeema.githubapp.databinding.FragmentFollowingBinding
import com.ayeshaazeema.githubapp.model.User
import com.ayeshaazeema.githubapp.viewmodel.FollowViewModel

class FollowingFragment : Fragment() {

    private var _followingBinding: FragmentFollowingBinding? = null
    private val followingBinding get() = _followingBinding!!
    private lateinit var followViewModel: FollowViewModel
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var listFollowing: ArrayList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _followingBinding = FragmentFollowingBinding.inflate(layoutInflater)
        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME) as String

        setAdapter()

        setViewModel(username)
    }

    override fun onResume() {
        super.onResume()
        showProgressBar(true)
    }

    private fun setAdapter() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        followingBinding.rvFollowing.apply {
            setHasFixedSize(true)
            adapter = followingAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            followingBinding.pbFollowing.visibility = View.VISIBLE
        } else {
            followingBinding.pbFollowing.visibility = View.GONE
        }
    }

    private fun setViewModel(username: String) {
        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)

        followViewModel.setListFollow(username, "following", this.requireContext())
        followViewModel.getListFollow().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                listFollowing = users
                followingAdapter.setData(listFollowing)
                showProgressBar(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _followingBinding = null
    }
}