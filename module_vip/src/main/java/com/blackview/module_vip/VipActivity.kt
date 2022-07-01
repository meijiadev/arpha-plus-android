package com.blackview.module_vip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blackview.base.App
import com.blackview.contant.device
import com.blackview.contant.vip
import com.blackview.repository.login.LoginActivity
import com.blackview.util.gotoAct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.io.path.Path

@Route(path = vip)
class VipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip)

         MainScope().launch(Dispatchers.Main){
             delay(2000)
             if (App.token.isNullOrEmpty()){
                 App.instance.gotoAct<LoginActivity>()
             }
         }
    }
}