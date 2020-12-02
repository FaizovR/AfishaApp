package ru.faizovr.afisha.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.faizovr.afisha.R
import ru.faizovr.afisha.presentation.fragment.CategoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(CategoryFragment())
        }
    }

    fun goToFragment(fragment: Fragment) {
        replaceFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment_container, fragment)
            .commit()
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(FRAGMENT_TAG)
            .commit()
    }

    companion object {
        private const val FRAGMENT_TAG = "TaskListFragment"
    }

}