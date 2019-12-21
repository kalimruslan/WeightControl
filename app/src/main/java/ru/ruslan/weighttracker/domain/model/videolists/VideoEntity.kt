package ru.ruslan.weighttracker.domain.model.videolists

import ru.ruslan.weighttracker.domain.model.videolists.ContentDetailsEntity
import ru.ruslan.weighttracker.domain.model.videolists.SnippetEntity

data class VideoEntity(
    val snippet: SnippetEntity,
    val contentDetails: ContentDetailsEntity
)