package com.cy.practice.simplecontact.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings


fun Context.toDial(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone" ))
    startActivity(intent)
}

fun Context.toSms(phone: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phone" ))
    startActivity(intent)
}

fun Context.toSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

