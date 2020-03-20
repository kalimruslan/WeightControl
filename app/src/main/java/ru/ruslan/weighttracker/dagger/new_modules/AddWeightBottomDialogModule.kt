package ru.ruslan.weighttracker.dagger.new_modules

import dagger.Module
import dagger.Provides
import ru.ruslan.weighttracker.dagger.scope.HomeScope
import ru.ruslan.weighttracker.ui.home.AddWeightBottomSheetDialog

@Module
class AddWeightBottomDialogModule {

    @HomeScope
    @Provides
    fun provideBottomSheetDialog(): AddWeightBottomSheetDialog = AddWeightBottomSheetDialog()
}