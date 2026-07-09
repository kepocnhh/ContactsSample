package test.android.contacts

import android.net.Uri
import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.DisconnectCause
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager

internal class CallLogsService : ConnectionService() {
    private val logger = App.providers.loggers.create("[CallLogs]")

    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?,
    ): Connection? {
        logger.debug("on create incoming connection")
        val connection = object : Connection() {
            init {
                connectionProperties = PROPERTY_SELF_MANAGED
                audioModeIsVoip = true
            }
        }
        val address: Uri? = request?.address
        connection.setAddress(address, TelecomManager.PRESENTATION_ALLOWED)
        val cdn = request?.extras?.getString("CallerDisplayName")
        if (!cdn.isNullOrEmpty()) {
            connection.setCallerDisplayName(cdn, TelecomManager.PRESENTATION_ALLOWED)
        }
        connection.setDisconnected(DisconnectCause(DisconnectCause.REJECTED))
        connection.destroy()
        return connection
    }
}
