package com.hieupt.standalonescrollbar.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by HieuPT on 11/1/2023.
 */
abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    protected abstract val viewBindingInflater: (LayoutInflater) -> VB

    protected lateinit var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = viewBindingInflater(layoutInflater)
        setContentView(viewBinding.root)
    }

    protected inline fun setupView(block: VB.() -> Unit) {
        viewBinding.block()
    }
}