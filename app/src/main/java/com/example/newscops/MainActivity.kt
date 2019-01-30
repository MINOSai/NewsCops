package com.example.newscops

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newscops.util.AlarmReceiver
import com.example.newscops.util.HeadlineWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import com.example.newscops.util.PreferenceHelper.set
import java.util.*
import javax.inject.Inject
import android.icu.util.ULocale.getCountry
import android.telephony.TelephonyManager
import com.example.newscops.util.PreferenceHelper


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var prefs: SharedPreferences

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countryCode = getUserCountry(this)
        prefs[PreferenceHelper.PREF_COUNTRY_KEY] = countryCode

        startAlarmManager()
    }

    private fun startAlarmManager() {

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
        }

        var alarmMgr: AlarmManager? = null
        lateinit var alarmIntent: PendingIntent
        alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        intent = Intent(applicationContext, AlarmReceiver::class.java)

        if (PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_NO_CREATE) == null) {
            alarmIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)

            alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )
        }

//        alarmIntent = Intent(applicationContext, AlarmReceiver::class.java).let { intent ->
//            PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
//        }

        //Uncomment this to test notifs
//        alarmMgr?.set(
//            AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime(),
//            alarmIntent
//        )
    }

    fun getUserCountry(context: Context): String? {
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = tm.simCountryIso
            if (simCountry != null && simCountry.length == 2) {
                return simCountry.toLowerCase(Locale.US)
            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) {
                val networkCountry = tm.networkCountryIso
                if (networkCountry != null && networkCountry.length == 2) {
                    return networkCountry.toLowerCase(Locale.US)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.fragment_nav_host).navigateUp()
}
