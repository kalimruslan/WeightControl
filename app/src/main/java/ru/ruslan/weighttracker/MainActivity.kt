package ru.ruslan.weighttracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_video_detail.*
import ru.ruslan.weighttracker.home.ui.HomeFragment
import ru.ruslan.weighttracker.videos.list.ui.VideosFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {

        }
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        clickListeners()

        bottom_navigation.selectedItemId = R.id.nav_main
    }

    private fun clickListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
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
