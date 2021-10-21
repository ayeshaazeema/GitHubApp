package com.ayeshaazeema.githubapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.adapter.ViewPagerAdapter
import com.ayeshaazeema.githubapp.databinding.ActivityDetailBinding
import com.ayeshaazeema.githubapp.model.User
import com.ayeshaazeema.githubapp.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sectionsPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tvFollowers: TextView
    private lateinit var tvFollowing: TextView

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) as String

        setViewPager()
        setTabLayout()
        showProgressBar(true)
        setViewModel(username)
    }

    private fun setViewModel(username: String) {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java
        )
        detailViewModel.setDetailUser(username, this)
        detailViewModel.getDetailUser().observe(this, { user ->
            if (user != null) {
                setData(user)
                showProgressBar(false)
            }
        })
    }

    private fun setData(user: User) {
        detailBinding.tvName.text = user.name
        detailBinding.tvLocation.text = user.location
        tvFollowers.text = user.followers.toString()
        tvFollowing.text = user.following.toString()

        Glide.with(this)
            .load(user.avatar)
            .into(detailBinding.imgAvatar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = user.username
        }
    }

    private fun setTabLayout() {
        val tabs: TabLayout = findViewById(R.id.tabsDetail)

        val customTabFollowers =
            LayoutInflater.from(this).inflate(R.layout.custom_tab_followers, tabs, false)
        tvFollowers = customTabFollowers.findViewById(R.id.tvFollowers)

        val customTabFollowing =
            LayoutInflater.from(this).inflate(R.layout.custom_tab_following, tabs, false)
        tvFollowing = customTabFollowing.findViewById(R.id.tvFollowing)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.customView = customTabFollowers
                1 -> tab.customView = customTabFollowing
            }
        }.attach()
    }

    private fun setViewPager() {
        sectionsPagerAdapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.vpDetail)
        viewPager.adapter = sectionsPagerAdapter
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            detailBinding.pbDetail.visibility = View.VISIBLE
        } else {
            detailBinding.pbDetail.visibility = View.GONE
        }
    }
}