package ru.chani.telpekremover.presentation.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun launchStartMenu()

    fun launchGame()

    fun launchResult()

}