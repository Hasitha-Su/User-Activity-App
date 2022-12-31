/**********************************************************************************************************
 * @author : Hasitha Subhashana
 * @Description  : Main activity to fetch image and user information
 * ========================================================================================================
 * @History
 * --------------------------------------------------------------------------------------------------------
 * VERSION     AUTHOR                    DATE         DETAIL
1.0    Hasitha Subhashana       23/12/2022   Initial implementation
 **********************************************************************************************************/
package com.hasithatest01.myapplication

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

class RegisterInfoActivity : AppCompatActivity() {

    lateinit var userImageView: ImageView

    lateinit var btnGetUserImage: Button
    lateinit var btnSubmit: Button

    lateinit var  editName :EditText
    lateinit var  editEmail :EditText
    lateinit var  editAge :EditText
    lateinit var  editPhoneNum :EditText
    private var userImageUri: Uri? = null

    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 101

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userImageView = findViewById(R.id.imageView)
        btnGetUserImage = findViewById(R.id.buttonLoadPicture)
        btnSubmit = findViewById(R.id.nextActivity)

        editName = findViewById(R.id.editTextTextPersonName)
        editEmail = findViewById(R.id.editTextTextEmailAddress6)
        editAge = findViewById(R.id.editTextNumber)
        editPhoneNum = findViewById(R.id.editTextPhone)

        btnGetUserImage.setOnClickListener {
            if (checkRequiresPermission(this@RegisterInfoActivity)) {
                selectProfileImageSource(this@RegisterInfoActivity)
            }
        }

        btnSubmit.setOnClickListener {
            val dataToSend = User(
                editName.text.toString(),
                editEmail.text.toString(),
                editAge.text.toString(),
                editPhoneNum.text.toString(),
                userImageUri.toString()
            )

            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.putExtra("userInfo", dataToSend);
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            //Captured from camera
            0 ->if (resultCode == RESULT_OK) {
                val selectedImage = data?.extras!!["data"] as Bitmap?
                userImageUri = selectedImage?.let { getImageUri(this@RegisterInfoActivity, it) }
                userImageView.setImageBitmap(selectedImage)
            }

            //Selected from gallery
            1 -> if (resultCode == RESULT_OK) {
                userImageUri = data?.data
                userImageView.setImageURI(userImageUri)
            }
        }
    }

    // function to let's the user to choose image from camera or gallery
    private fun selectProfileImageSource(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Camera",
            "Choose from Gallery",
            "Exit"
        ) // create a menu option Array

        val builder = AlertDialog.Builder(context)

        builder.setItems(optionsMenu) { dialogInterface, i ->

            if (optionsMenu[i] == "Camera") {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)

            } else if (optionsMenu[i] == "Choose from Gallery") {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)

            } else if (optionsMenu[i] == "Exit") {
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    //Check if necessary permissions are provided
    private fun checkRequiresPermission(context: Activity?): Boolean {

        val writeToExtStorePermission = ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

        val requiresPermissionList: MutableList<String> = ArrayList()

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            requiresPermissionList.add(Manifest.permission.CAMERA)
        }

        if (writeToExtStorePermission != PackageManager.PERMISSION_GRANTED) {
            requiresPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (requiresPermissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions( context, requiresPermissionList.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }

        return true
    }

    //Convert Bitmap images from camera to Uri
    private fun getImageUri(inContext: Context, image: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, image, "Title", null)
        return Uri.parse(path)
    }

}