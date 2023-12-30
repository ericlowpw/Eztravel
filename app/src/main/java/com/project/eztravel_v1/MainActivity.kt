package com.project.eztravel_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.project.eztravel_v1.databinding.ActivityMainBinding

// https://www.youtube.com/watch?v=Eu7lkrDjBq8 - Splash Screen
// 288x288 , 192x192 , 192x192 (circle)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Fragment1())

        binding.fragBtn1.setOnClickListener {
            replaceFragment(Fragment1())
        }
        binding.fragBtn2.setOnClickListener {
            replaceFragment(Fragment2())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}