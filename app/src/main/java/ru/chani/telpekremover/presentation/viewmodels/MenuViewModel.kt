package ru.chani.telpekremover.presentation.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.chani.telpekremover.R
import ru.chani.telpekremover.data.RepositoryImp
import ru.chani.telpekremover.domain.entity.GameResult
import ru.chani.telpekremover.domain.usecase.GetRecordOfTheBestGameResult

open class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryImp(application)
    private val getRecordOfTheBestGameResult = GetRecordOfTheBestGameResult(repository)

    private val context = application
    private lateinit var mainThemeMusic: MediaPlayer

    private val _gameBestResult = MutableLiveData<GameResult>()
    val gameBestResult: LiveData<GameResult> = _gameBestResult


    fun init() {
        getBestScore()
        initMusic()
        playMusic()
    }

    fun playMusic() {
        mainThemeMusic.start()
    }

    fun pauseMusic() {
        mainThemeMusic.pause()
    }

    override fun onCleared() {
        mainThemeMusic.stop()
        super.onCleared()
    }

    private fun getBestScore(){
        _gameBestResult.value = getRecordOfTheBestGameResult()
    }

    private fun initMusic() {
        mainThemeMusic = MediaPlayer.create(context, R.raw.main_theme)
        mainThemeMusic.isLooping = true
    }
}