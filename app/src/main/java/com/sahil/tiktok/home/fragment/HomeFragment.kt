package com.sahil.tiktok.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sahil.tiktok.*
import com.sahil.tiktok.extension.toast
import com.sahil.tiktok.home.adapter.ViewPagerAdapter
import com.sahil.tiktok.home.viewmodel.VideoDataViewModel
import com.sahil.tiktok.model.ResponseData
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment: Fragment() {

    private var root: View?= null
    private lateinit var videoPagerAdapter: ViewPagerAdapter
    private var videoDataViewModel:  VideoDataViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        videoDataViewModel = ViewModelProvider(this)[VideoDataViewModel::class.java]
        videoDataViewModel?.getVideoList(requireContext())

        root?.viewPager?.offscreenPageLimit = 4

        videoDataViewModel?.response?.observe(viewLifecycleOwner, Observer {
            when(it){

                is ResponseData.ERROR ->{
                    it.error.printStackTrace()
                    requireContext().toast("Failed to fetch list")
                }

                is ResponseData.SUCCESS.VideoData -> {
                    videoPagerAdapter = ViewPagerAdapter(this, it.response)
                    root?.viewPager?.adapter = videoPagerAdapter
                }

                else -> {
                    requireContext().toast("Failed to fetch list")
                }
            }
        })

        return root
    }

}