package ru.chani.telpekremover.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.chani.telpekremover.R
import ru.chani.telpekremover.presentation.contract.Navigator

class MainActivity : AppCompatActivity(), Navigator {

    private var currentFragment = VALUE_START_MENU_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.activity_main)

        launchRightFragment(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString(KEY_NAME_OF_CURRENT_FRAGMENT, currentFragment)
        super.onSaveInstanceState(outState, outPersistentState)
    }


    private fun launchRightFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            launchStartMenu()
        } else {
            val value = savedInstanceState.getString(
                KEY_NAME_OF_CURRENT_FRAGMENT,
                VALUE_START_MENU_FRAGMENT
            )
            when (value) {
                VALUE_START_MENU_FRAGMENT -> launchStartMenu()
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    override fun launchStartMenu() {
        val startMenuFragment = StartMenuFragment.newInstance()
        launchFragment(fragment = startMenuFragment)
    }

    override fun launchGame() {
        TODO("Not yet implemented")
    }

    override fun launchResult() {
        TODO("Not yet implemented")
    }

    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    companion object {
        private const val KEY_NAME_OF_CURRENT_FRAGMENT = "KEY_NAME_OF_CURRENT_FRAGMENT"

        private const val VALUE_START_MENU_FRAGMENT = "VALUE_START_MENU_FRAGMENT"
    }
}