package com.sunasterisk.fooddaily.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.facebook.FacebookSdk
import com.sunasterisk.fooddaily.R

abstract class BaseActivity: AppCompatActivity() {

    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this.applicationContext)
        setContentView(layoutRes)
        initView()
        initPresenter()
    }

    abstract fun initPresenter()
    abstract fun initView()

    fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContent, fragment)
            .commit()
    }
}
