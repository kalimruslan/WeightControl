package ru.ruslan.weighttracker.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
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
import ru.ruslan.weighttracker.ui.util.showToast
import ru.ruslan.weighttracker.ui.util.startActivityExt
import javax.inject.Inject

@SplashScope
class SplashActivity : AppCompatActivity(), SplashContract.View {

    @Inject lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).getAppComponent().getSplashComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if(checkGooglePlayService())
            retrieveFirebaseToken()
        presenter.setView(this)
    }

    private fun checkGooglePlayService(): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        return status == ConnectionResult.SUCCESS
    }

    private fun retrieveFirebaseToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("retrieveFirebaseToken", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            // 3
            val token = task.result?.token

            // 4
            val msg = getString(R.string.token_prefix, token)
            Log.d("retrieveFirebaseToken", msg)
            // TODO реализовать сохранение токена где нить
        })
    }

    override fun openNextScreen(userExist: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            if(userExist) startActivityExt<MainActivity>(this@SplashActivity)
            else startActivityExt<ProfileActivity>(this@SplashActivity)
            finish()
        }
    }
}
