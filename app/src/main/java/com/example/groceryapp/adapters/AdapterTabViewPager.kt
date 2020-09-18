package com.example.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryapp.fragments.ProductFragment

class AdapterTabViewPager (fm: FragmentManager): FragmentPagerAdapter(fm){

    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    fun dataChanged() {
        notifyDataSetChanged()
    }

    fun addFragment(tabTitle: String, subId: Int){
        mFragmentList.add(ProductFragment.newInstance(tabTitle, subId))
        mTitleList.add(tabTitle)
    }
}