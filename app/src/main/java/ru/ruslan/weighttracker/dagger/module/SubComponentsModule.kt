package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import ru.ruslan.weighttracker.dagger.subcomponents.CameraSubComponent
import ru.ruslan.weighttracker.dagger.subcomponents.HomeSubComponent

@Module(subcomponents = [CameraSubComponent::class, HomeSubComponent::class])
class SubComponentsModule