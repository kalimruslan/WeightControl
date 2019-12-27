package ru.ruslan.weighttracker.ui.profile.vm.model


class ProfileUI(val fio: String?,
                var currentWeight: Double = 60.0,
                var currentHeight: Double = 160.0,
                var sex: String = "",
                var imt: String = "",
                val goalWeight: Double = 0.0,
                val measuresMap: Map<String, String>?)