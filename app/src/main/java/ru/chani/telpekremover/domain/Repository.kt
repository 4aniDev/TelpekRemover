package ru.chani.telpekremover.domain

import ru.chani.telpekremover.domain.entity.GameResult

interface Repository {
    fun putRecordOfTheBestScore(gameResult: GameResult)
    fun getRecordOfTerBestScore(): GameResult

}