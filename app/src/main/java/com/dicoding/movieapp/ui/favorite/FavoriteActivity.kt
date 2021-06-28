package com.dicoding.movieapp.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.movieapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val favSectionAdapter = FavPagerAdapter(this, supportFragmentManager)
        activityFavoriteBinding.apply {
            viewPager.adapter = favSectionAdapter
            tabs.setupWithViewPager(activityFavoriteBinding.viewPager)

            supportActionBar?.elevation = 0f
        }
    }
}