package com.blackview.module_vip.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.databinding.FragmentDevicesBinding
import com.blackview.module_vip.databinding.FragmentShareBinding


/**
 *    author : MJ
 *    time   : 2022\07\05
 *    desc   : 接收装置
 */
class ShareFragment :BaseMVFragment<FragmentDevicesBinding,BaseViewModel> (){

    companion object{
        fun newInstance():ShareFragment{
            val args = Bundle()
            val fragment = ShareFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun createViewModel(fragment: Fragment): BaseViewModel {
        return ViewModelProvider(this).get(BaseViewModel::class.java)
    }

}