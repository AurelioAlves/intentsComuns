package com.example.intentscomuns

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi

const val REQUEST_SELECT_CONTACT = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.main_button1).setOnClickListener {
            val intent = Intent( Intent.ACTION_DIAL )
            startActivity( intent )
        }

        findViewById<Button>(R.id.main_button2).setOnClickListener {
            val intent = Intent( Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI ).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
            }

            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }

        findViewById<Button>(R.id.main_button3).setOnClickListener {
            val intent = Intent( Intent.ACTION_WEB_SEARCH ).apply {
                putExtra(SearchManager.QUERY, "Android Studio")
            }

            startActivity( intent )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK ) {
            val contactUri: Uri? = data?.data
            var displayName = ""
            var number = ""
            if (contactUri != null) {
                contentResolver.query(contactUri, null, null, null, null).use{ cursor ->
                    if (cursor != null) {
                        if(cursor.moveToFirst()) {
                            val displayIndex =
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                            displayName = cursor.getString(displayIndex)
                            contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                "DISPLAY_NAME = '$displayName'", null, null).use { cursorNumber ->
                                if( cursorNumber != null) {
                                    if(cursorNumber.moveToFirst()) {
                                        val numberIndex =
                                            cursorNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                        number = cursorNumber.getString(numberIndex)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val intent = Intent(this, MostrarContatoActivity::class.java)
            intent.putExtra("cName", displayName)
            intent.putExtra("cPhone", number)

            startActivity( intent )
        }


    }
}