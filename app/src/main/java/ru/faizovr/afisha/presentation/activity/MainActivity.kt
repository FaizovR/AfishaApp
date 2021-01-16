package ru.faizovr.afisha.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.faizovr.afisha.R
import ru.faizovr.afisha.presentation.fragment.CategoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        if (savedInstanceState == null) {
            addFragment(CategoryFragment.newInstance())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    addFragment(CategoryFragment.newInstance())
                } else {
                    supportFragmentManager.popBackStack()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

}