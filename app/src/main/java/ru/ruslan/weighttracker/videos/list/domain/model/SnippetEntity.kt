package ru.ruslan.weighttracker.videos.list.domain.model

data class SnippetEntity(val title: String,
                         val description: String,
                         val thumbnailsEntity: SnippetThumbnailsEntity?,
                         val channelTitle: String)
