package ru.chani.telpekremover.presentation.fragments

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.chani.telpekremover.R
import ru.chani.telpekremover.databinding.FragmentGameBinding
import ru.chani.telpekremover.presentation.viewmodels.GameViewModel

class GameFragment : Fragment() {


    private lateinit var viewModel: GameViewModel
    private val mapOfIv = mutableMapOf<Int, ImageView>()

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        putImageViewsToMap()
        setObservers()
        setOnClickListeners()
        viewModel.startNewGame()
    }

    override fun onPause() {
        viewModel.pauseGame()
        super.onPause()
    }


    override fun onResume() {
        viewModel.resumeGame()
        super.onResume()
    }

    private fun putImageViewsToMap() {
        mapOfIv[1] = binding.ivHole1
        mapOfIv[2] = binding.ivHole2
        mapOfIv[3] = binding.ivHole3
        mapOfIv[4] = binding.ivHole4
        mapOfIv[5] = binding.ivHole5
        mapOfIv[6] = binding.ivHole6
        mapOfIv[7] = binding.ivHole7
        mapOfIv[8] = binding.ivHole8
        mapOfIv[9] = binding.ivHole9
    }

    private fun setObservers() {
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimerValue.text = it
        }

        viewModel.currentPositionOfPerson.observe(viewLifecycleOwner) {
            setPersonVisibleInCurrentHole(it)
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            binding.tvScoreValue.text = it.scoreString
        }

        viewModel.lastHit.observe(viewLifecycleOwner) {
            // if hit to person, then change animation to beaten person
            if (it.hit) {
                mapOfIv[it.holeId]?.let { holeId -> startDisappearanceAnim(holeId) }
            }
        }

        viewModel.gameFinish.observe(viewLifecycleOwner) {
            Toast.makeText(context, " Game FINISH", Toast.LENGTH_LONG).show()
        }
    }

    private fun setOnClickListeners() {
        mapOfIv.forEach { (keyNumOfHole, imageView) ->
            imageView.setOnClickListener {
                viewModel.selectedHole(keyNumOfHole)
            }
        }
    }


    private fun setPersonVisibleInCurrentHole(currentHole: Int) {
        mapOfIv[currentHole]?.let { imageView -> startAppearanceAnim(imageView) }
    }

    private fun startAppearanceAnim(imageView: ImageView) {
        imageView.setImageResource(R.drawable.person_anim)
        startAnimation(imageView)
    }

    private fun startDisappearanceAnim(imageView: ImageView) {
        imageView.setImageResource(R.drawable.person_beaten_anim)
        startAnimation(imageView)
    }

    private fun startAnimation(imageView: ImageView) {
        val frameAnimation = imageView.drawable as AnimatedVectorDrawable
        frameAnimation.start()
    }


    companion object {
        fun newInstance() = GameFragment()
    }
}