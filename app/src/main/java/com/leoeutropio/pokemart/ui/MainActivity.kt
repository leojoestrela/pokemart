package com.leoeutropio.pokemart.ui


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.leoeutropio.pokemart.R
import com.leoeutropio.pokemart.databinding.ActivityMainBinding
import com.leoeutropio.pokemart.ui.fragment.HistoryFragment
import com.leoeutropio.pokemart.ui.fragment.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            configToolbar()
            configDrawerLayout()

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment()).commit()
                navView.setCheckedItem(R.id.nav_home)
            }

            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment()).commit()
                    }

                    R.id.nav_historico -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HistoryFragment()).commit()
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun ActivityMainBinding.configDrawerLayout() {
        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            toolbarActivityMain,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    private fun ActivityMainBinding.configToolbar() {
        setSupportActionBar(toolbarActivityMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarActivityMain.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(binding.navView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.alt_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

