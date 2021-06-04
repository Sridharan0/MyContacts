package com.example.mycontacts

import android.app.Application

class MyContactsApplication : Application() {

    val TAG = MyContactsApplication::class.java
        .simpleName

    companion object {
        lateinit var mInstance: MyContactsApplication
        fun getInstance(): MyContactsApplication {
            return mInstance
        }
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this

    }

    override fun onTerminate() {
        super.onTerminate()
    }

}