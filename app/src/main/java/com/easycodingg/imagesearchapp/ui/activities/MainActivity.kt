package com.easycodingg.imagesearchapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.easycodingg.imagesearchapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        bottomNavBar.setupWithNavController(navHost.findNavController())

        bottomNavBar.setOnNavigationItemReselectedListener { /*NO-OP*/ }

        navHost.findNavController().addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.discoverFragment, R.id.searchFragment, R.id.likedFragment -> {
                    appBarLayout.visibility = View.VISIBLE
                    bottomNavBar.visibility = View.VISIBLE
                }
                R.id.splashFragment -> {
                    appBarLayout.visibility = View.GONE
                    bottomNavBar.visibility = View.GONE
                }
                else -> {
                    appBarLayout.visibility = View.VISIBLE
                    bottomNavBar.visibility = View.GONE
                }
            }
        }
    }
}