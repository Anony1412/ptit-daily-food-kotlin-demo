package com.sunasterisk.fooddaily.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sunasterisk.fooddaily.R

abstract class BaseFragment: Fragment() {

    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getArgument()
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionBar()
        initPresenter()
    }

    abstract fun initActionBar()
    abstract fun getArgument()
    abstract fun initPresenter()

    fun switchByReplaceFragment(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frameContent, fragment)
            ?.commit()
    }

    fun switchByAddFragment(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.frameContent, fragment)
            ?.commit()
    }

    fun removeFragment(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(fragment)
            ?.commit()
    }
}
