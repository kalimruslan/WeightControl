package ru.ruslan.weighttracker.videos.list.vm.model

class VideoUI(val title: String,
              val description: String,
              val url: String){
    constructor() : this("","","'")
}