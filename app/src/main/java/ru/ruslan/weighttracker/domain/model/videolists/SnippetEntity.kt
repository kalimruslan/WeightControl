package ru.ruslan.weighttracker.domain.model.videolists

data class SnippetEntity(val title: String,
                         val description: String,
                         val thumbnailsEntity: SnippetThumbnailsEntity?,
                         val channelTitle: String)
