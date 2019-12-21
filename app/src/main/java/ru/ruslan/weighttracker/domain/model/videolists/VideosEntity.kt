package ru.ruslan.weighttracker.domain.model.videolists

import ru.ruslan.weighttracker.domain.model.videolists.VideoEntity

data class VideosEntity(val nextPageToken: String,
                        val resultPerPage: Int,
                        val totalResult: Int,
                        val items: List<VideoEntity>)