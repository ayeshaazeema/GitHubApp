package com.ayeshaazeema.githubapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.adapter.FollowerAdapter
import com.ayeshaazeema.githubapp.adapter.ViewPagerAdapter
import com.ayeshaazeema.githubapp.databinding.FragmentFollowerBinding
import com.ayeshaazeema.githubapp.model.User
import com.ayeshaazeema.githubapp.viewmodel.FollowViewModel

class FollowerFragment : Fragment() {

    private var _followerBinding: FragmentFollowerBinding? = null
    private val followerBinding get() = _followerBinding!!
    private lateinit var followViewModel: FollowViewModel
    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var listFollower: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _followerBinding = FragmentFollowerBinding.inflate(inflater, container, false)
        return followerBinding.root
    }
}