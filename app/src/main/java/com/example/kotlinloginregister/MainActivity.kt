package com.example.kotlinloginregister

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<View>(R.id.fragment_page) as CustomViewPager
        viewPager.setPagingEnabled(false)

        val adapter = MyAdapter(supportFragmentManager)

        viewPager.adapter = adapter
        viewPager.currentItem = 1
    }

    class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return RegisterFragment()
                1 -> return LoginFragment()
                2 -> return ResetPasswordFragment()
                else -> return RegisterFragment()
            }
        }
    }
}