package test.android.contacts

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.telecom.DisconnectCause
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

internal class MainActivity : ComponentActivity() {
    private val logger = App.providers.loggers.create("[Main]")

    private fun onAddCallLog() {
        logger.debug("on add call log")
        val context: Context = this
        val tm = context.getSystemService(TelecomManager::class.java)
        val handle = PhoneAccountHandle(
            ComponentName(context, CallLogsService::class.java),
            "${BuildConfig.APPLICATION_ID}:call_logs_handle",
        )
        val extras = Bundle()
//        val dc = DisconnectCause.LOCAL
//        val dc = DisconnectCause.REMOTE
//        val dc = DisconnectCause.MISSED
        val dc = DisconnectCause.REJECTED
        val cdn = "user$dc"
        extras.putParcelable(
            TelecomManager.EXTRA_INCOMING_CALL_ADDRESS,
            Uri.fromParts("sip", "$cdn@foo.org", null),
        )
        extras.putString("CallerDisplayName", cdn)
        extras.putInt("DisconnectCause", dc)
        tm.addNewIncomingCall(handle, extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        LinearLayout(context).also { root ->
            root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            root.orientation = LinearLayout.VERTICAL
            root.gravity = Gravity.CENTER_VERTICAL
            Button(context).also { view ->
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                view.text = "add call log"
                view.setOnClickListener { _ ->
                    onAddCallLog()
                }
                root.addView(view)
            }
            setContentView(root)
        }
    }
}
