package ru.ruslan.weighttracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.ui.home.HomeFragment
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.videos.list.VideosFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        when(item.itemId){
            R.id.action_profile -> startActivity(Intent(this, ProfileActivity::class.java))
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_main -> {
                openFragment(HomeFragment.newInstance(), item.title.toString())
            }
            R.id.nav_video -> {
                openFragment(VideosFragment.newInstance(), item.title.toString())
                return true
            }
            R.id.nav_settings -> return true
        }
        return false
    }

    private fun openFragment(fragment: Fragment, title: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.fl_container, fragment)
            addToBackStack(null)
            commit()
        }
        supportActionBar?.title = title
    }
}
