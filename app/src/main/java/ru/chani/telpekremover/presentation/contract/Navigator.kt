package ru.chani.telpekremover.presentation.contract

import androidx.fragment.app.Fragment
import ru.chani.telpekremover.domain.entity.GameResult

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun launchStartMenu()

    fun launchGame()

    fun launchResult(gameResult: GameResult)

}