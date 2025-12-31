package com.practicum.work_test_project.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course (
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val isFavorite: Boolean,
    val publishDate: String
) : Parcelable