package com.example.mycontacts.ux.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.example.mycontacts.ux.model.Contacts


object ContactUtils {

    fun getContactList(context: Context): ArrayList<Contacts> {
        val contacts = arrayListOf<Contacts>()
        val cr: ContentResolver = context.contentResolver
        val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER/*, ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.LABEL, ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY*/
        )

        val cur: Cursor? = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if ((cur?.count ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {

                val name: String = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                val phoneNum: String =
                    cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val contact = Contacts(name, phoneNum)
                contacts.add(contact)
            }
        }
        cur?.close()
        return contacts
    }

}