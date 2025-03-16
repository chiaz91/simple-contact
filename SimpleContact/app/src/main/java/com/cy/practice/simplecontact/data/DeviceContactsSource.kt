package com.cy.practice.simplecontact.data

import android.content.Context
import android.provider.ContactsContract
import com.cy.practice.simplecontact.domain.model.Contact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceContactsSource (
    context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val contentResolver = context.contentResolver

    suspend fun getContacts(): List<Contact> = withContext(ioDispatcher) {
        val contactsList = mutableListOf<Contact>()
        val selection = "${ContactsContract.Contacts.HAS_PHONE_NUMBER} = ?"
        val selectionArgs = arrayOf("1")
        val sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
            ),
            selection,
            selectionArgs,
            sortOrder
        )
        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val photoIndex = it.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
            val photoThumbIndex = it.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex)
                val photoUri = it.getString(photoIndex)
                val photoThumbUri = it.getString(photoThumbIndex)
                val phoneNumbers = getPhoneNumbers(id)
                contactsList.add(Contact(id, name, photoUri, photoThumbUri, phoneNumbers))
            }
        }
        return@withContext contactsList
    }


    private fun getPhoneNumbers(contactId: String): List<String> {
        val phoneNumbers = mutableListOf<String>()
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
//            ContactsContract.CommonDataKinds.Phone.TYPE,
//            ContactsContract.CommonDataKinds.Phone.LABEL
        )
        val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
        val selectionArgs = arrayOf(contactId)
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )
        cursor?.use {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
//            val typeIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)
//            val labelIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL)
            while (it.moveToNext()) {
                val number = cursor.getString(numberIndex)
//                val type = cursor.getInt(typeIndex)
//                val customLabel = cursor.getString(labelIndex)
//
//                val label = ContactsContract.CommonDataKinds.Phone.getTypeLabel(
//                    res, type, customLabel
//                ).toString()
                phoneNumbers.add(number)
            }
        }
        return phoneNumbers
    }
}