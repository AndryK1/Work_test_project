package com.practicum.work_test_project.ui.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.work_test_project.R
import com.practicum.work_test_project.databinding.FragmentProfileBinding
import com.practicum.work_test_project.domain.entity.ProfileCoursesData
import com.practicum.work_test_project.ui.profile.ProfileAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    //В тз сказано экран-заглушка, так что пока прокидываю mock объекты прямо так
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mockCourses = listOf(
            ProfileCoursesData(
                title = "3D-дженералист",
                rating = "3.9",
                date = "10 Сентября 2024",
                completedLessons = 22,
                totalLessons = 44,
                imageRes = R.drawable.photo_placeholder
            ),
            ProfileCoursesData(
                title = "Android разработчик",
                rating = "4.5",
                date = "2 Августа 2024",
                completedLessons = 10,
                totalLessons = 60,
                imageRes = R.drawable.photo_placeholder
            ),
            ProfileCoursesData(
                title = "UI / UX Designer",
                rating = "4.2",
                date = "15 Июля 2024",
                completedLessons = 48,
                totalLessons = 48,
                imageRes = R.drawable.photo_placeholder
            )
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ProfileAdapter(mockCourses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}