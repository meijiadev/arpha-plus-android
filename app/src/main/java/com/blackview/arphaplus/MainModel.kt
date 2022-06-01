package com.blackview.arphaplus

import androidx.databinding.ObservableField
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.apiService
import com.blackview.repository.apiService2
import com.blackview.repository.repository.RepositoryFactory
import com.blackview.util.L

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * Created by home on 2022/5/30.
 */
class MainModel : BaseViewModel() {

    val repository = RepositoryFactory.createByAccountSession(MainRepository::class.java)

    //val repository=MainRepository()
    var string = repository.string


    fun getData() {
        requestNoCheck({ apiService2.getData() }, {
            L.e(it.toString())
        })
    }


}