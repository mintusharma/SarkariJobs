package com.geekdroid.sarkarinaukri.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.geekdroid.sarkarinaukri.R

class BubbleNavigation : AppCompatActivity() {
    var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_navigation)

    /*    val toolbar:Toolbar=findViewById(R.id.toolbarHome)
        toolbar.setTitle("Home")

        val bubbleNavigation = findViewById<BubbleBottomNavigationLinearView>(R.id.bottom_navigation_view_linear)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                HomeFragment()).commit()
        bubbleNavigation.setNavigationChangeListener { view, position ->
            when (position) {
                0 -> selectedFragment = HomeFragment()
                1 -> selectedFragment = LatestJobFragment()
                2 -> selectedFragment = AdmitCardFragment()
                3 -> selectedFragment = SyllabusFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    selectedFragment!!).commit()
        }*/
    }
}