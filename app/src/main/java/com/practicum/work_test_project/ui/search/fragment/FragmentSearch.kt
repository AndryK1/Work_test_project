package com.practicum.work_test_project.ui.search.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.work_test_project.databinding.FragmentSearchBinding
import com.practicum.work_test_project.domain.entity.Course
import com.practicum.work_test_project.ui.search.CoursesAdapter
import com.practicum.work_test_project.ui.search.SearchState
import com.practicum.work_test_project.ui.search.viewModel.SearchViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSearch  : Fragment(){
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel : SearchViewModel by viewModel()

    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView
        setupRecyclerView()

        //Настройка поля ввода
        setupEditText()

        // Наблюдение за состояниями
        observeViewModel()
    }

    private fun setupEditText() {

        binding.emailInputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let { query ->
                    viewModel.searchDebounce(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                state?.let { renderState(it) }
            }
        }
    }

    private fun renderState(state: SearchState){
        when(state){
            is SearchState.Empty -> {
                binding.vacancyList.isVisible = false
                binding.progressBar.isVisible = false
            }
            is SearchState.Loading -> {
                binding.vacancyList.isVisible = false
                binding.progressBar.isVisible = true
            }
            is SearchState.Content -> {
                binding.progressBar.isVisible = false
                binding.vacancyList.isVisible = true

                adapter.updateList(state.courses)
            }
            is SearchState.Error -> {
                binding.progressBar.isVisible = false
                binding.vacancyList.isVisible = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshFavorites()
    }

    private fun refreshFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.refreshFavorites()
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
            adapter = this@FragmentSearch.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCourseDetails(course: Course) {

        val action = FragmentSearchDirections.actionFragmentSearchToDetailsFragment(course)
        findNavController().navigate(action)

    }
}