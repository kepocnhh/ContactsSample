package test.android.contacts

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

internal class MainActivity : ComponentActivity() {
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
                    val cv = ContentValues()
                    val now = System.currentTimeMillis()
                    val number = "+7${now % 1_000_000_0000}"
                    cv.put(CallLog.Calls.NUMBER, number)
                    try {
                        context.contentResolver.insert(CallLog.Calls.CONTENT_URI, cv)
                    } catch (error: Throwable) {
                        Log.w("[Main]", "add call log error: $error")
                    }
                }
                root.addView(view)
            }
            setContentView(root)
        }
    }
}
