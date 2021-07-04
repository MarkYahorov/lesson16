package com.example.lesson16

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MainFragment.ButtonClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var drLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAll()
    }

    private fun initAll() {
        toolbar = findViewById(R.id.toolbar)
        recycler = findViewById(R.id.nav_list)
        drLayout = findViewById(R.id.drawer_layout)
    }

    override fun onStart() {
        super.onStart()
        setToolbar()
        setRecycler()
        createDrawer()
        replaceTheFragment(MainFragment(), MainFragment.TAG)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecycler() {
        val list = listOf(
            Category(R.drawable.home_image, "Главная"),
            Category(R.drawable.settings_image, "Настройки")
        )
        with(recycler) {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = NavDrawerAdapter(list,
                OnClick = {
                    if (list[0] == it) {
                        replaceTheFragment(MainFragment(), MainFragment.TAG)
                    } else {
                        replaceTheFragment(SettingsFragment(), "settingsFragment")
                    }
                    onBackPressed()
                })
        }
    }

    private fun createDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            drLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.setToolbarNavigationClickListener {
            onBackPressed()
        }
        drLayout.addDrawerListener(toggle)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    override fun onBackPressed() {
        if (drLayout.isDrawerVisible(GravityCompat.START)) {
            drLayout.closeDrawer(GravityCompat.START)
            return
        }

        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
            updateToolbarBtn()
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(string: String) {
        val bundle = Bundle()
        val rightTextFragment = RightTextFragment()
        bundle.putString("THIS", string)
        rightTextFragment.arguments = bundle
        replaceTheFragment(rightTextFragment, "rightTextFragment")
    }

    private fun replaceTheFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.some_container, fragment, tag)
            .addToBackStack(null)
            .commit()
        supportFragmentManager.executePendingTransactions()
        updateToolbarBtn()
    }

    private fun updateToolbarBtn() {
        toggle.isDrawerIndicatorEnabled = supportFragmentManager.backStackEntryCount <= 1
    }
}