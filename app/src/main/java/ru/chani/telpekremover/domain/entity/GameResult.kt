package ru.chani.telpekremover.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(val score: Int) : Parcelable
