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
import com.aditya.searchgithub.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {
    private var followersBinding: FragmentFollowersBinding? = null
    private val binding get() = followersBinding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val user = arguments
        username = user?.getString(DetailActivity.EXTRA_USERNAME).toString()

        followersBinding = FragmentFollowersBinding.inflate(layoutInflater)


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.getFollowers().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setListUser(it)
                sLoading(false)
            }
        })

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding?.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.adapter = adapter
        }

        sLoading(true)

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followersBinding = null
    }

    private fun sLoading(state: Boolean){
        if (state){
            binding?.followersProgressBar?.visibility = View.VISIBLE
        } else{
            binding?.followersProgressBar?.visibility = View.GONE
        }
    }
}