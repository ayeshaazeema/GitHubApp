package com.ayeshaazeema.githubapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayeshaazeema.githubapp.activity.DetailActivity
import com.ayeshaazeema.githubapp.adapter.FollowersAdapter
import com.ayeshaazeema.githubapp.databinding.FragmentFollowersBinding
import com.ayeshaazeema.githubapp.model.User
import com.ayeshaazeema.githubapp.viewmodel.FollowViewModel

class FollowersFragment : Fragment() {

    private var _followersBinding: FragmentFollowersBinding? = null
    private val followersBinding get() = _followersBinding!!
    private lateinit var followViewModel: FollowViewModel
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var listFollowers: ArrayList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _followersBinding = FragmentFollowersBinding
            .inflate(inflater, container, false)
        return followersBinding.root
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
        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()

        followersBinding.rvFollowers.apply {
            setHasFixedSize(true)
            adapter = followersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            followersBinding.pbFollowers.visibility = View.VISIBLE
        } else {
            followersBinding.pbFollowers.visibility = View.GONE
        }
    }

    private fun setViewModel(username: String) {
        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)

        followViewModel.setListFollow(username, "followers", this.requireContext())
        followViewModel.getListFollow().observe(viewLifecycleOwner, { users ->
            if (users != null) {
                listFollowers = users
                followersAdapter.setData(listFollowers)
                showProgressBar(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _followersBinding = null
    }
}