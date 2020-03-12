package ru.ruslan.weighttracker.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ruslan.weighttracker.MainApplication
import ru.ruslan.weighttracker.R
import ru.ruslan.weighttracker.dagger.scope.SplashScope
import ru.ruslan.weighttracker.domain.contract.SplashContract
import ru.ruslan.weighttracker.ui.MainActivity
import ru.ruslan.weighttracker.ui.profile.ProfileActivity
import ru.ruslan.weighttracker.ui.util.startActivityExt
import javax.inject.Inject

@SplashScope
class SplashActivity : AppCompatActivity(), SplashContract.View {

    @Inject lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).getAppComponent().getSplashComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        presenter.setView(this)
    }

    override fun openNextScreen(userExist: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            if(userExist) startActivityExt<MainActivity>(this@SplashActivity)
            else startActivityExt<ProfileActivity>(this@SplashActivity)
            finish()
        }
    }
}
