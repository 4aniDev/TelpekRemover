package ru.chani.telpekremover.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import ru.chani.telpekremover.domain.Repository
import ru.chani.telpekremover.domain.entity.GameResult


class RepositoryImp(context: Context) : Repository {

    private val shpBestScore = context.getSharedPreferences(
        SHARED_PREFERENCE_BEST_SCORE,
        AppCompatActivity.MODE_PRIVATE
    )


    override fun getRecordOfTerBestScore(): GameResult {
        return GameResult(scoreInt = getRecordOfTheBestScore())
    }


    private fun getRecordOfTheBestScore(): Int {
        return shpBestScore.getInt(
            SHARED_PREFERENCE_BEST_SCORE,
            SHARED_PREFERENCE_DEFAULT_BEST_SCORE)
    }


    override fun putRecordOfTheBestScore(gameResult: GameResult) {
        val editor = shpBestScore.edit()
        editor.putInt(
            SHARED_PREFERENCE_BEST_SCORE,
            gameResult.scoreInt
        )
        editor.apply()
    }


    companion object {

        private const val SHARED_PREFERENCE_BEST_SCORE = "SHARED_PREFERENCE_BEST_SCORE"

        private const val SHARED_PREFERENCE_DEFAULT_BEST_SCORE = 0
    }
}