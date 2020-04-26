package com.digital.hospital

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.digital.hospital.databinding.HomeActivityBinding

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: this.aliahsan@gmail.com
 *         Created on: 24/04/20
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Bottom navigation bar
        setupBottomNavMenu(host.navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController)
    }
}
