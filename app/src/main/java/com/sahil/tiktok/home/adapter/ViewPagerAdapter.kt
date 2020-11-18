package com.sahil.tiktok.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sahil.tiktok.home.fragment.VideoViewFragment
import com.sahil.tiktok.model.VideoDataModel

class ViewPagerAdapter(fragment: Fragment, private val videoDataList: MutableList<VideoDataModel> = mutableListOf()) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return VideoViewFragment.newInstance(videoDataList[position])
    }

    override fun getItemCount(): Int {
        return videoDataList.size
    }

}