package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import ru.ruslan.weighttracker.dagger.subcomponents.*

@Module(
    subcomponents = [
        SplashSubComponent::class,
        CameraSubComponent::class,
        HomeSubComponent::class,
        ProfileSubComponent::class,
        VideoListSubComponent::class
    ]
)
class SubComponentsModule