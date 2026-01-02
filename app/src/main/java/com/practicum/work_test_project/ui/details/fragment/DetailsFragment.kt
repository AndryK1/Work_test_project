package com.practicum.work_test_project.ui.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.practicum.work_test_project.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.practicum.work_test_project.databinding.FragmentDetailsBinding
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.ui.details.viewModel.DetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class DetailsFragment  : Fragment(){
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DetailsViewModel by viewModel()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseData = args.course

        setupScreen(courseData)
        // Наблюдение за состояниями
        observeViewModel()

        binding.leaveButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.favoriteButton.setOnClickListener {
            addCourseToFavorite(courseData)
        }

        loadInitialFavoriteStatus(courseData.id)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.favoriteState.collect { isFavorite ->
                isFavorite?.let {
                    updateFavoriteIcon(it)
                }
            }
        }
    }

    private fun addCourseToFavorite(course: Course) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.toggleFavorite(course)
        }
    }

    private fun loadInitialFavoriteStatus(courseId: Long) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.checkFavoriteStatus(courseId)
        }
    }


    private fun setupScreen(course: Course){
        binding.profileTitle.text = course.title
        binding.rateText.text = course.rate
        binding.publishedDate.text = course.publishDate
        binding.academyName.text = course.title
        binding.description.text = course.description
    }

    private fun updateFavoriteIcon(isFavorite: Boolean){
        if (isFavorite){
            binding.favoriteButton.setImageResource(R.drawable.ic_favorites_fill)
            ImageViewCompat.setImageTintList(binding.favoriteButton, ContextCompat.getColorStateList(context, R.color.green))
        }else{
            binding.favoriteButton.setImageResource(R.drawable.ic_favorites_24)
            ImageViewCompat.setImageTintList(binding.favoriteButton, ContextCompat.getColorStateList(context, R.color.darkGray))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}