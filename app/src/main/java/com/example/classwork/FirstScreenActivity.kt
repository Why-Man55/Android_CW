package com.example.classwork

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

class FirstScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fromNotif = intent?.getStringExtra("EXTRA_TITLE")
        if (!fromNotif.isNullOrEmpty()) {
            Toast.makeText(this, fromNotif, Toast.LENGTH_LONG).show()
            intent.removeExtra("EXTRA_TITLE")
        }

        setContent {
            FirstScreen()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val fromNotif = intent.getStringExtra("EXTRA_BODY")
        if (!fromNotif.isNullOrEmpty()) {
            Toast.makeText(this, fromNotif, Toast.LENGTH_LONG).show()
        }
    }
}

const val ACTION_NOTIF_DISMISSED = "com.example.classwork.NOTIF_DISMISSED"

@Composable
fun FirstScreen() {
    val context = LocalContext.current

    var notifId by remember { mutableIntStateOf(1000) }

    var headText by remember { mutableStateOf("") }
    var bodyText by remember {mutableStateOf("") }
    var isCancellable by remember { mutableStateOf(false) }
    var dropMenuExpanded by remember { mutableStateOf(false) }
    var dropMenuContent by remember { mutableStateOf("MIN") }
    var refactorText by remember {mutableStateOf("")}

    var showDeniedScreen by remember {mutableStateOf(false)}
    var isAnyNotif by remember { mutableStateOf(hasActiveNotification(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        showDeniedScreen = !isGranted
    }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context1: Context?, intent: Intent?) {
                if (intent?.action == ACTION_NOTIF_DISMISSED) {
                    isAnyNotif = hasActiveNotification(context)
                }
            }
        }

        val filter = IntentFilter(ACTION_NOTIF_DISMISSED)
        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    LaunchedEffect(Unit) {
        if (!hasNotificationPermission(context) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        showDeniedScreen = !hasNotificationPermission(context)
        isAnyNotif = hasActiveNotification(context)
    }

    val prepareSend = {
        val importance = when (dropMenuContent) {
            "MIN" -> {
                NotificationManager.IMPORTANCE_MIN
            }
            "HIGH" -> {
                NotificationManager.IMPORTANCE_HIGH
            }
            else -> {
                NotificationManager.IMPORTANCE_DEFAULT
            }
        }

        createNotifChannel(context, dropMenuContent, importance)
        notifId++
        sendNotification(context, headText, bodyText, dropMenuContent, notifId, isCancellable)
        isAnyNotif = true
    }

    if (showDeniedScreen) {
        Column {
            Text(stringResource(R.string.notif_perm_error_screen_text))

            Button(
                onClick = { openSettings(context)}
            ) {
                Text(stringResource(R.string.notif_perm_error_screen_button))
            }
        }
    } else {
        Column {
            TextField(
                value = headText,
                onValueChange = { headText = it },
                label = {Text(stringResource(R.string.notif_title_label)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            TextField(
                value = bodyText,
                onValueChange = { bodyText = it },
                label = {Text(stringResource(R.string.notif_body_label)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Checkbox(
                checked = isCancellable,
                onCheckedChange = {
                    isCancellable = !isCancellable
                }
            )

            Box {
                OutlinedButton(onClick = { dropMenuExpanded = true }) {
                    Text("Важность: $dropMenuContent")
                }
                DropdownMenu(
                    expanded = dropMenuExpanded,
                    onDismissRequest = { dropMenuExpanded = false }
                ) {
                    listOf("MIN", "DEFAULT", "HIGH").forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                dropMenuContent = item
                                dropMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (hasNotificationPermission(context)) {
                        prepareSend()
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            ) {
                Text(stringResource(R.string.notif_send_button))
            }

            Row {
                TextField(
                    value = refactorText,
                    onValueChange = {
                        refactorText = it
                    },
                    label = {Text(stringResource(R.string.notif_refactor_button))},
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    enabled = isAnyNotif
                )

                Text("Последнее уведомление: $notifId")
            }

            Row {
               Button(
                   onClick = {
                       val id = refactorText.toInt()
                       if (hasActiveNotifWithId(context, id)) {
                           sendNotification(context, headText, bodyText, dropMenuContent, id, isCancellable)
                       } else {
                           Toast.makeText(context,
                               context.getString(R.string.notif_error_null_id_toast), Toast.LENGTH_SHORT).show()
                       }
                   }
               ) {
                   Text(stringResource(R.string.notif_refactor_button))
               }
               Button(
                   onClick = {
                       if (isAnyNotif) {
                           NotificationManagerCompat.from(context).cancelAll()
                           isAnyNotif = false
                       } else {
                           Toast.makeText(context,
                               context.getString(R.string.notif_error_empty_toast), Toast.LENGTH_SHORT).show()
                       }
                   },
                   enabled = isAnyNotif
               ) {
                   Text(stringResource(R.string.notif_delete_button))
               }
            }
        }
    }
}

private fun hasActiveNotifWithId(context: Context, id: Int): Boolean {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return manager.activeNotifications.any {it.id == id}
}

private fun hasActiveNotification(context: Context) : Boolean {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val activeNotif = manager.activeNotifications

    return activeNotif.isNotEmpty()
}

private fun hasNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

private fun sendNotification(context: Context, head: String, body: String, type:String, id:Int, isCancellable: Boolean) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        return
    }

    val clickIntent = Intent(context, FirstScreenActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        putExtra("EXTRA_TITLE", head)
        putExtra("EXTRA_BODY", body)
    }

    val clickPendingIntent = PendingIntent.getActivity(
        context,
        id,
        clickIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val deleteIntent = Intent(ACTION_NOTIF_DISMISSED).apply {
        setPackage(context.packageName)
    }

    val deletePendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        deleteIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, type + "_channel")
        .setSmallIcon(android.R.drawable.ic_lock_silent_mode)
        .setContentTitle(head)
        .setContentText(body)
        .setOngoing(isCancellable)
        .setAutoCancel(!isCancellable)
        .setContentIntent(clickPendingIntent)
        .setDeleteIntent(deletePendingIntent)
        .setOnlyAlertOnce(true)

    NotificationManagerCompat.from(context).notify(id, builder.build())
}

private fun createNotifChannel(context: Context, type: String, importance: Int) {
    val channel = NotificationChannel(type + "_channel", type, importance)
        .apply { description = "канал для типа - $type" }
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(channel)
}

private fun openSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}