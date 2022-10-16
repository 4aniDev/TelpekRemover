package ru.chani.telpekremover.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    var scoreInt: Int = DEFAULT_SCORE,
    var scoreString: String = scoreInt.toString()
) : Parcelable {
    companion object {
        const val DEFAULT_SCORE = 0
    }
}

