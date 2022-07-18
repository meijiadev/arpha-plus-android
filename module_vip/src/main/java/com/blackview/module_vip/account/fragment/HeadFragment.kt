package com.blackview.module_vip.account.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.FragmentHeadBinding
import com.blackview.module_vip.main.AccountInfo
import com.blackview.module_vip.pictureselector.GlideEngine
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.style.PictureSelectorUIStyle
import com.luck.picture.lib.tools.MediaUtils
import com.orhanobut.logger.Logger
import java.io.File


/**
 *    author : MJ
 *    time   : 2022/07/15
 *    desc   : 更改头像界面
 */
class HeadFragment : BaseMVFragment<FragmentHeadBinding, BaseViewModel>() {
    private var launcherResult: ActivityResultLauncher<Intent>? = null

    private var cropImagePath: String? = null

    private var accountModel: AccountModel? = null
    override fun createViewModel(fragment: Fragment): BaseViewModel {
        return ViewModelProvider(this).get(BaseViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 注册需要写在onCreate或Fragment onAttach里，否则会报java.lang.IllegalStateException异常
        launcherResult = createActivityResultLauncher()
    }


    override fun initView() {
        super.initView()
        accountModel = getActivityViewModel(AccountModel::class.java)
        accountModel?.titleAction(getString(com.blackview.common_res.R.string.replace_head_image))
        binding.ivHeadImage.load(
            AccountInfo.headImage ?: com.blackview.common_res.R.drawable.empty_head
        ){
            transformations(
                RoundedCornersTransformation(20f)
            )
        }
    }

    override fun initData() {
        super.initData()
        accountModel?.headUpdateEvent?.observe(viewLifecycleOwner) {
            it?.let {
                AccountInfo.headImage = it
                findNavController().popBackStack()
            }
        }
    }

    override fun initListener() {
        super.initListener()
        // 添加头像
        binding.btAddImage.setOnClickListener {
            Logger.i("新增用户")
            toPictureSelector()
           // write failure:gatt writeCharacteristic fail

        }
        // 保存头像
        binding.btSave.setOnClickListener {
            cropImagePath?.let {
                accountModel?.changeHeadImage(File(it))
            }
        }
    }

    /**
     * 点击去图片选择器
     */
    private fun toPictureSelector() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
            .setPictureUIStyle(PictureSelectorUIStyle.ofSelectTotalStyle())
            .selectionMode(PictureConfig.SINGLE)
            .isPreviewImage(false)
            .isSingleDirectReturn(true)
            .isCamera(false)
            .isCompress(true)
            .isEnableCrop(true)
            .cutOutQuality(80)
            .withAspectRatio(1, 1)
            .forResult(launcherResult)
    }

    /**
     * 图片返回
     */
    private fun createActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val resultCode = result?.resultCode
            if (resultCode == Activity.RESULT_OK) {
                val selectList = PictureSelector.obtainMultipleResult(result.data)
                // 例如 LocalMedia 里面返回五种path
                // 1.media.getPath(); 原图path
                // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                for (media in selectList) {
                    if (media.width == 0 || media.height == 0) {
                        if (PictureMimeType.isHasImage(media.mimeType)) {
                            val imageExtraInfo = MediaUtils.getImageSize(media.path)
                            media.width = imageExtraInfo.width
                            media.height = imageExtraInfo.height
                        } else if (PictureMimeType.isHasVideo(media.mimeType)) {
                            val videoExtraInfo =
                                MediaUtils.getVideoSize(context, media.path)
                            media.width = videoExtraInfo.width
                            media.height = videoExtraInfo.height
                        }
                    }
                    Logger.i(
                        "是否压缩:${media.isCompressed}" +
                                "\n压缩: ${media.compressPath} " +
                                "\n 原图：${media.path} " +
                                "\n 绝对路径：${media.realPath}" +
                                "\n 是否裁剪：${media.isCut} " +
                                "\n 裁剪：${media.cutPath}" +
                                "\n 是否开启原图:${media.isOriginal}" +
                                "\n 原图路径:${media.originalPath}" +
                                "\n Android Q 特有Path:${media.androidQToPath}" +
                                "\n 宽高:${media.width} x:${media.height}" +
                                "\n Size:${media.size} "

                    )
                    cropImagePath = media.cutPath
                    binding.ivHeadImage.load(File(cropImagePath))
                }

            }
        }
    }


    override fun initViewObservable() {
        super.initViewObservable()
    }

}