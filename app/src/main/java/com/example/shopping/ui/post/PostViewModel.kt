package com.example.shopping.ui.post

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping.R
import com.example.shopping.data.network.Output
import com.example.shopping.data.repository.UploadPostRepository
import com.example.shopping.util.CameraUtil
import com.example.shopping.util.Coroutines
import com.example.shopping.util.getActivity
import com.example.shopping.util.replaceFramgnet
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_post.view.*
import kotlinx.coroutines.Job
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class PostViewModel(
    private val repository: UploadPostRepository
) : ViewModel() {

    private lateinit var job: Job

    var product_img : File? = null
    var product_name : String? = null
    var product_detail : String? = null
    var product_price : String? = null
    var product_stock : String? = null
    var product_major_category = ""
    var product_minor_category = ""
    val product_mark : String? = null
    var product_merchandiser : String? = "happy"

    val major_category_list = arrayOf("의류", "식료품", "전자제품")
    val minor_category_list = arrayOf(arrayOf("상의","하의","아우터","신발"),
        arrayOf("신선식품","가공식품"), arrayOf("컴퓨터","가전제품","모바일"))

     fun setPermission(v: View) {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {//설정해 놓은 위험권한(카메라 접근 등)이 허용된 경우 이곳을 실행
                showChoicePhotoDialog(v.context)

            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {//설정해 놓은 위험권한이 거부된 경우 이곳을 실행
                Toast.makeText(v.context,"요청하신 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(v.context)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    private fun showChoicePhotoDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("사진을 선택해주세요")
            .setCancelable(false)
            .setPositiveButton("카메라") { dialog, id ->
                CameraUtil.getInstance(context).dispatchTakePictureIntent()
            }
            .setNegativeButton("앨범") { dialog, id ->
                // Dismiss the dialog
                openGallery(context)
            }
        val alert = builder.create()
        alert.show()
    }

    val REQUEST_IMAGE_PICK = 10

    private fun openGallery(context: Context) {
        val activity = context.getActivity()

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = MediaStore.Images.Media.CONTENT_TYPE
        //galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        activity?.startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    private val _majorCategory = MutableLiveData<String>()
    val majorCategory : LiveData<String>
        get() = _majorCategory

    private val _minor_Category = MutableLiveData<String>()
    val minorCategory : LiveData<String>
        get() = _minor_Category

    fun showCategoryDialog(v: View, isMajor: Boolean){
        System.out.println("실행")
        if(isMajor){

            val mBuilder = AlertDialog.Builder(v.context)


            var checked = -1

            if(product_major_category.isNotEmpty()){
                for(j in major_category_list.indices){
                    if(product_major_category == major_category_list[j]){
                        checked = j
                    }
                }
            }

            mBuilder.setSingleChoiceItems(major_category_list, checked) { dialogInterface, i ->
                product_major_category = major_category_list[i]
                _majorCategory.value = major_category_list[i]
                product_minor_category = ""
                _minor_Category.value = ""
                dialogInterface.dismiss()
            }
            val mDialog = mBuilder.create()
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val listView: ListView = mDialog.listView
            listView.dividerHeight = 20 // set height

            mDialog.show()

        }else{
                if(product_major_category.isNotEmpty()){
                    for(i in major_category_list.indices){
                        if(product_major_category == major_category_list[i]){
                            val mBuilder = AlertDialog.Builder(v.context)

                            var checked = -1

                            if(product_minor_category.isNotEmpty()){
                                for(j in minor_category_list[i].indices){
                                    if(product_minor_category == minor_category_list[i][j]){
                                        checked = j
                                    }
                                }
                            }

                            mBuilder.setSingleChoiceItems(minor_category_list[i], checked) { dialogInterface, pos ->
                                product_minor_category = minor_category_list[i][pos]
                                _minor_Category.value = minor_category_list[i][pos]
                                dialogInterface.dismiss()
                            }


                            val mDialog = mBuilder.create()
                            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            val listView: ListView = mDialog.listView
                            listView.dividerHeight = 20 // set height

                            mDialog.show()

                        }
                    }
                }
        }
    }

    fun activityFinish(v: View) {
        val activity = v.context.getActivity()
        activity?.finish()
    }


    fun replaceFragment(from: Fragment, to: Fragment, addToBackStack: Boolean, v: View){
        v.context.replaceFramgnet(from, to, addToBackStack, R.id.main_post_frame, false)
    }

    private val _isUploadSuccess = MutableLiveData<Boolean>()
    val isUploadSuccess : LiveData<Boolean>
        get() = _isUploadSuccess

    private fun uploadProduct(
        image: MultipartBody.Part,
        newProduct: RequestBody,
        productDetail: RequestBody,
        productPrice: RequestBody,
        productStock: RequestBody,
        productMajorCategory: RequestBody,
        productMinorCategory: RequestBody,
        productMerchandiser: RequestBody
    ){
        job = Coroutines.ioThenMain(
            { repository.uploadProduct(image, newProduct, productDetail, productPrice, productStock, productMajorCategory,
                productMinorCategory, productMerchandiser) },
            {
                when( it ){
                    is Output.Success -> _isUploadSuccess.value = true
                    // do something with success result
                    is Output.Error -> System.out.println(it.exception.message)
                }
            }
        )
    }

     fun startPost() {


        val productName: RequestBody = RequestBody.create(MediaType.parse("text/plain"), product_name)
        val productDetail: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"), product_detail)
        val productPrice: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"), product_price)
        val productStock: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"), product_stock)
        val productMajorCategory: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"), product_major_category)
        val productMinorCategory: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"), product_minor_category)
        val productMerchandiser: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"), product_merchandiser)

        //creating a file
        var fileName = "post_img" + Date().time.toString()
        fileName += ".png"


        val requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),product_img)


        val image = MultipartBody.Part.createFormData("product_image",fileName,requestBody)


        uploadProduct(image,  productName, productDetail, productPrice, productStock,
           productMajorCategory, productMinorCategory, productMerchandiser)

    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized) job.cancel()
    }
}