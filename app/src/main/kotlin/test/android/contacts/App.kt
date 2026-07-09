package test.android.contacts

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val loggers: Loggers = FinalLoggers()
        _providers = Providers(
            loggers = loggers,
        )
        val context: Context = this
        val handle = PhoneAccountHandle(ComponentName(context, CallLogsService::class.java), "${BuildConfig.APPLICATION_ID}:call_logs_handle")
        val pa = PhoneAccount.builder(handle, "${BuildConfig.APPLICATION_ID}:call_logs")
            .setCapabilities(PhoneAccount.CAPABILITY_SELF_MANAGED)
            .build()
        val tm = context.getSystemService(TelecomManager::class.java)
        tm.registerPhoneAccount(pa)
    }

    companion object {
        private var _providers: Providers? = null
        val providers: Providers get() = checkNotNull(_providers) { "No providers!" }
    }
}
