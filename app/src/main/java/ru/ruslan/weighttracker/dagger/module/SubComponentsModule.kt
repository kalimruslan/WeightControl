package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import ru.ruslan.weighttracker.dagger.subcomponents.CameraSubComponent
import ru.ruslan.weighttracker.dagger.subcomponents.HomeSubComponent
import ru.ruslan.weighttracker.dagger.subcomponents.ProfileSubComponent
import ru.ruslan.weighttracker.dagger.subcomponents.SplashSubComponent

@Module(subcomponents = [SplashSubComponent::class, CameraSubComponent::class, HomeSubComponent::class, ProfileSubComponent::class])
class SubComponentsModule