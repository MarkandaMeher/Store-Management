package com.example.mystore

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class LoginAdapter : FragmentPagerAdapter{

   lateinit var context : Context
   private var totalTabs : Int

    constructor(fm: FragmentManager, context: Context,totalTabs : Int) : super(fm){
        this.context = context
        this.totalTabs = totalTabs
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {

        return when(position){
            0 -> {
                LoginTabFrag()
            }
            1 -> {
                OwnerTabFrag()
            }
            else ->{
                null!!
            }
        }
    }


    override fun getPageTitle(position: Int): CharSequence? {

        return when(position){
            0 -> {
                "Login"
            }
            1 -> {
                "Owner"
            }
            else ->{
                null
            }
        }
    }
}