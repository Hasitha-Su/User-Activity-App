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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RegisterInfoActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var button: Button
    lateinit var button2: Button

    private val pickImage = 100
    private var imageUri: Uri? = null

    lateinit var  editName :EditText
    lateinit var  editEmail :EditText
    lateinit var  editAge :EditText
    lateinit var  editPhone :EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.buttonLoadPicture)
        button2 = findViewById(R.id.nextActivity)

        editName = findViewById(R.id.editTextTextPersonName)
        editEmail = findViewById(R.id.editTextTextEmailAddress6)
        editAge = findViewById(R.id.editTextNumber)
        editPhone = findViewById(R.id.editTextPhone)

        button.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        button2.setOnClickListener {
            val dataToSend = User(
                editName.text.toString(),
                editEmail.text.toString(),
                editAge.text.toString(),
                editPhone.text.toString(),
                imageUri.toString()
            )

            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.putExtra("message_key", dataToSend);
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }
}