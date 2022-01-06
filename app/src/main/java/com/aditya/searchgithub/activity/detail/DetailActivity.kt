package com.aditya.searchgithub.activity.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.aditya.searchgithub.R
import com.aditya.searchgithub.activity.detail.fragment.SectionPagerAdapter
import com.aditya.searchgithub.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )

        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val back = binding.back
        back.setOnClickListener {
            super.onBackPressed()
        }


        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        setPagerView(bundle)

        setViewModel(username)
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch { 
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.favoriteDetail.isChecked = true
                        isChecked = true
                    }else{
                        binding.favoriteDetail.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.favoriteDetail.setOnClickListener {
            isChecked = !isChecked
            if (isChecked){
                    viewModel.addFavorite(username, id, avatarUrl)
            }else{
                viewModel.removeFavorite(id)
            }
            binding.favoriteDetail.isChecked = isChecked
        }

        showLoading(true)
    }


    private fun setViewModel(user: String?){
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.setDetail(user)
        viewModel.getDetail().observe(this, { detailUser ->
            CoroutineScope(Dispatchers.Main).launch{
                delay(1000)
                if (detailUser != null){
                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(detailUser.avatar_url)
                            .circleCrop()
                            .into(detailImage)
                        showLoading(false)
                        name.text = detailUser.name
                        username.text = detailUser.login
                        jmlFollower.text = detailUser.followers.toString()
                        jmlFollow.text = detailUser.following.toString()
                        repo.text = detailUser.public_repos.toString()
                        if (detailUser.company != null){
                            com.text = detailUser.company
                        } else{
                            com.text = "-"
                        }
                        if (detailUser.location != null){
                            loc.text = detailUser.location
                        } else{
                            loc.text = "-"
                        }
                    }
                }
            }
        })
    }

    private fun setPagerView(data: Bundle){
        val sectionPagerAdapter = SectionPagerAdapter(this, data)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter =sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.detailProgressBar.visibility = View.VISIBLE
        } else{
            binding.detailProgressBar.visibility = View.GONE
        }
    }
}
