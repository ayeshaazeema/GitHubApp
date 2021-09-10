package com.ayeshaazeema.githubapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.adapter.ViewPagerAdapter
import com.ayeshaazeema.githubapp.databinding.ActivityDetailBinding
import com.ayeshaazeema.githubapp.databinding.ActivitySplashBinding
import com.ayeshaazeema.githubapp.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sectionPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tvFollower: TextView
    private lateinit var tvFollowing: TextView

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        setViewPager()
    }

    private fun setViewPager() {
        sectionPagerAdapter = ViewPagerAdapter(this)
        viewPager = detailBinding.vpDetail
        viewPager.adapter = sectionPagerAdapter
    }
}