package com.vipul.materialmotions

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale

class MainActivity : AppCompatActivity() {

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.fab)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            floatingActionButton.visibility = when (destination.id) {
                R.id.firstFragment -> {
                    View.VISIBLE
                }
                else -> {
                    View.GONE
                }
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            currentNavigationFragment?.apply {
                exitTransition = MaterialElevationScale(false).apply {
                    duration = 700
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = 700
                }
            }
            navController.navigate(FirstFragmentDirections.actionListToFabDetails())
        }
    }
}