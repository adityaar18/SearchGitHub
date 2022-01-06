package com.aditya.searchgithub.activity.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.searchgithub.activity.ListUserAdapter
import com.aditya.searchgithub.activity.detail.DetailActivity
import com.aditya.searchgithub.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private var followingBinding: FragmentFollowingBinding? = null
    private val binding get() = followingBinding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val user = arguments
        username = user?.getString(DetailActivity.EXTRA_USERNAME).toString()
        followingBinding = FragmentFollowingBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner, { followers ->
            if (followers != null){
                adapter.setListUser(followers)
                sLoading(false)
            }
        })

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding?.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }

        sLoading(true)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followingBinding = null
    }

    private fun sLoading(state: Boolean){
        if (state){
            binding?.followingProgressBar?.visibility = View.VISIBLE
        } else{
            binding?.followingProgressBar?.visibility = View.GONE
        }
    }
}