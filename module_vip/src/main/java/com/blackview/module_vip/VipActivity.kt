package com.blackview.module_vip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blackview.contant.device
import com.blackview.contant.vip
import kotlin.io.path.Path

@Route(path = vip)
class VipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip)
    }
}