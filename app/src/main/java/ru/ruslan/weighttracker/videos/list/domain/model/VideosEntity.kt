package ru.ruslan.weighttracker.videos.list.domain.model

data class VideosEntity(val nextPageToken: String,
                        val resultPerPage: Int,
                        val totalResult: Int,
                        val items: List<VideoEntity>)