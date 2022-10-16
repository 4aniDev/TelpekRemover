package ru.chani.telpekremover.presentation.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.chani.telpekremover.R
import ru.chani.telpekremover.data.RepositoryImp
import ru.chani.telpekremover.domain.entity.CountDownTimerExt
import ru.chani.telpekremover.domain.entity.GameResult
import ru.chani.telpekremover.domain.entity.Hit
import ru.chani.telpekremover.domain.usecase.GetRandomPersonPosition
import ru.chani.telpekremover.domain.usecase.GetRecordOfTheBestGameResult
import ru.chani.telpekremover.domain.usecase.PutRecordOfTheBestGameResult

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImp(application)

    private val getRandomPersonPosition = GetRandomPersonPosition()
    private val putRecordOfTheBestScore = PutRecordOfTheBestGameResult(repository)
    private val getRecordOfTheBestScore = GetRecordOfTheBestGameResult(repository)

    private val context = application

    private var gameTimer: CountDownTimerExt? = null
    private var respawnTimer: CountDownTimerExt? = null

    private lateinit var desertSound: MediaPlayer
    private lateinit var gameMusic: MediaPlayer
    private lateinit var listOfPatSounds: List<MediaPlayer>

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> = _formattedTime

    private var _currentPositionOfPerson = MutableLiveData<Int>()
    val currentPositionOfPerson: LiveData<Int> = _currentPositionOfPerson

    private val _lastHit = MutableLiveData<Hit>()
    val lastHit: LiveData<Hit> = _lastHit

    private val _gameResult = MutableLiveData<GameResult>(GameResult())
    val gameResult: LiveData<GameResult> = _gameResult

    private val _gameFinish = MutableLiveData<Unit>()
    val gameFinish: LiveData<Unit> = _gameFinish


    private var currentScore = GameResult.DEFAULT_SCORE
    private var isSkipRespawnByTimer = false

    fun startNewGame() {
        initializeGameTimer()
        initializeRespawnPersonTimer()
        setSounds()
        playGameSounds()
    }

    fun pauseGame() {
        gameTimer?.pause()
        respawnTimer?.pause()
        pauseGameSounds()
    }

    fun resumeGame() {
        gameTimer?.start()
        respawnTimer?.start()
        playGameSounds()
    }

    private fun initializeGameTimer() {
        gameTimer = object : CountDownTimerExt(
            DEFAULT_GAME_TIME_IN_MILLIS,
            MILLIS_IN_SECONDS
        ) {

            override fun onTimerTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onTimerFinish() {
                finishGame()
            }
        }
        gameTimer?.start()
    }

    private fun initializeRespawnPersonTimer() {
        respawnTimer = object : CountDownTimerExt(
            DEFAULT_GAME_TIME_IN_MILLIS,
            TIME_IN_MILLIS_FOR_RESPAWN_OF_PERSON
        ) {

            override fun onTimerTick(millisUntilFinished: Long) {
                updatePositionOfPerson(BY_TIMER)
                isSkipRespawnByTimer = false
            }

            override fun onTimerFinish() {}
        }
        respawnTimer?.start()
    }

    private fun updatePositionOfPerson(updateMethod: Boolean) {
        if (updateMethod == BY_TAP) {
            isSkipRespawnByTimer = true
            _currentPositionOfPerson.value = getRandomPersonPosition()
        } else {
            if (!isSkipRespawnByTimer) {
                _currentPositionOfPerson.value = getRandomPersonPosition()
            }
        }
    }

    fun selectedHole(numberOfSelectedHole: Int) {
        if (numberOfSelectedHole == currentPositionOfPerson.value) {
            _lastHit.value = Hit(holeId = numberOfSelectedHole, hit = true)
            _gameResult.value = GameResult(scoreInt = currentScore++)
            updatePositionOfPerson(BY_TAP)
            listOfPatSounds.random().start()
        }
    }

    private fun finishGame() {
        compareAndWriteResult()
        stopGameSounds()
        _gameFinish.value = Unit
    }

    private fun compareAndWriteResult() {
        if (currentScore > getRecordOfTheBestScore().scoreInt) {
            putRecordOfTheBestScore(gameResult = gameResult.value ?: GameResult())
        }
    }

    private fun playGameSounds() {
        gameMusic.start()
        desertSound.start()
    }

    private fun pauseGameSounds() {
        desertSound.pause()
        gameMusic.pause()
    }

    private fun stopGameSounds() {
        desertSound.stop()
        gameMusic.stop()
    }

    private fun setSounds() {
        setPatSounds()
        setDesertSound()
        setGameMusic()
    }

    private fun setGameMusic() {
        val randomId = getRandomIdOfGameMusic()
        gameMusic = MediaPlayer.create(context, randomId)
    }

    private fun setPatSounds() {
        listOfPatSounds = arrayListOf<MediaPlayer>(
            MediaPlayer.create(context, R.raw.pat_1),
            MediaPlayer.create(context, R.raw.pat_2),
            MediaPlayer.create(context, R.raw.pat_3),
            MediaPlayer.create(context, R.raw.pat_4),
            MediaPlayer.create(context, R.raw.pat_5),
        )
    }

    private fun setDesertSound() {
        desertSound = MediaPlayer.create(context, R.raw.desert)
    }

    private fun getRandomIdOfGameMusic(): Int {
        val listOfDutarMusic = arrayListOf<Int>(
            R.raw.dutar_1,
            R.raw.dutar_2,
            R.raw.dutar_3,
            R.raw.dutar_4,
            R.raw.dutar_5,
        )
        return listOfDutarMusic.random()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        return seconds.toString()
    }


    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val DEFAULT_GAME_TIME_IN_MILLIS = 30000L

        private const val TIME_IN_MILLIS_FOR_RESPAWN_OF_PERSON = 750L

        private const val BY_TAP = true
        private const val BY_TIMER = false
    }
}