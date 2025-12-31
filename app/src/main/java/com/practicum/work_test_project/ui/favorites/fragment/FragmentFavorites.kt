package com.practicum.work_test_project.ui.favorites.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.work_test_project.databinding.FragmentFavoritesBinding
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.ui.favorites.FavoritesState
import com.practicum.work_test_project.ui.favorites.viewModel.FavoritesViewModel
import com.practicum.work_test_project.ui.search.CoursesAdapter
import com.practicum.work_test_project.ui.search.fragment.FragmentSearchDirections
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFavorites : Fragment(){
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : FavoritesViewModel by viewModel()

    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView
        setupRecyclerView()

        // Наблюдение за состояниями
        observeViewModel()

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteState.collect { state ->
                renderState(state)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CoursesAdapter(
            courses = emptyList() ,
            onItemClick = { course ->
                navigateToCourseDetails(course)
            },
            onFavoriteClick = {course ->
                viewModel.toggleFavorite(course)
            })
        binding.vacancyList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FragmentFavorites.adapter
        }
    }

    private fun renderState(state: FavoritesState){
        when(state){
            is FavoritesState.Empty -> {
                binding.vacancyList.isVisible = false
                binding.progressBar.isVisible = false
            }
            is FavoritesState.Content -> {
                binding.progressBar.isVisible = false
                binding.vacancyList.isVisible = true

                adapter.updateList(state.favoritesList)
            }
            is FavoritesState.Loading -> {
                binding.progressBar.isVisible = true
                binding.vacancyList.isVisible = false
            }
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            viewModel.refreshFavorites()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCourseDetails(course: Course) {

        val action = FragmentFavoritesDirections.actionFragmentFavoritesToDetailsFragment(course)
        findNavController().navigate(action)

    }
}