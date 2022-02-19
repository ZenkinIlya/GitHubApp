package com.example.githubapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.githubapp.R
import com.example.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set ActionBar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        //Builder with ids fragments which do not have upButton
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.loginFragment,
            R.id.listRepositoryFragment
        ).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

//        navController.addOnDestinationChangedListener({ controller, destination, arguments ->
//            destination.id ==
//        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}