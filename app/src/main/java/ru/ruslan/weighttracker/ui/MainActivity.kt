package ru.ruslan.weighttracker.ui

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.home.HomeFragment
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.util.Constants.APP_ACTIVITY
import ru.ruslan.weighttracker.ui.util.replaceFragment
import ru.ruslan.weighttracker.ui.util.startActivityExt
import ru.ruslan.weighttracker.ui.videos.list.VideoListFragment

class MainActivity : BaseActivity(R.layout.activity_main),
    BottomNavigationView.OnNavigationItemSelectedListener {
    override fun initDagger() {}

    override fun initMembers() {
        APP_ACTIVITY = this
        setSupportActionBar(main_toolbar)
        clickListeners()

        val toolbar = findViewById<MaterialToolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        bottom_navigation.selectedItemId = R.id.nav_main
    }

    private fun clickListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_profile -> startActivityExt<ProfileActivity>(this)
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_main -> {
                replaceFragment(HomeFragment.newInstance(), title = item.title.toString())
                return true
            }
            R.id.nav_video -> {
                replaceFragment(VideoListFragment.newInstance(), title = item.title.toString())
                return true
            }
            R.id.nav_settings -> return true
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
