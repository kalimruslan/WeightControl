package ru.ruslan.weighttracker.dagger.module

import dagger.Module
import ru.ruslan.weighttracker.dagger.subcomponents.CameraSubComponent

@Module(subcomponents = [CameraSubComponent::class])
class SubComponentsModule