package com.example.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.blogapp.core.hide
import com.example.blogapp.core.show
import com.example.blogapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment //Creamos una instancia de NavHostFragment
        val navController = navHostFragment.navController //Creamos el navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
          when(destination.id){
              R.id.loginFragment -> {
                  binding.bottomNavigationView.hide()
              }

              R.id.registerFragment -> {
                  binding.bottomNavigationView.hide()
              }

              R.id.setupProfileFragment -> {
                  binding.bottomNavigationView.hide()
              }

              else -> {
                  binding.bottomNavigationView.show()
              }
          }

        }
    }
}