package ru.trfx.dreams.core.dream

import java.time.LocalDateTime

data class Dream(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val colorArgb: Int,
    val isFavorite: Boolean,
    val isLucid: Boolean,
)
