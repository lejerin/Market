package com.example.shopping.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shopping.R
import com.example.shopping.ui.home.HomeFragment
import com.example.shopping.ui.post.PostActivity
import com.example.shopping.util.startPostActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeFragment: HomeFragment =
        HomeFragment()
    private val pageFragment: PageFragment =
        PageFragment()

    private lateinit var menuBawah: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuBawah = findViewById(R.id.menu_bawah)
        setFragment(homeFragment)


        menuBawah.setSelectedItemId(R.id.menu_home)
        menuBawah.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (menuItem.isChecked) {
                true
            } else {
                when (menuItem.itemId) {
                    R.id.menu_register -> {
                        val intent = Intent(this, PostActivity::class.java)
                        startActivity(intent)
                        false
                    }
                    else -> {
                        setFragment(homeFragment)
                        //   getSupportActionBar().setTitle("Home");
                        true
                    }
                }
            }
        })
    }

    private fun setFragment(fragment: Fragment) {
        val ft =
            supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_frame, fragment)
        ft.commit()
    }
}