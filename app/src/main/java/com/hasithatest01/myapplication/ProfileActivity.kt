/**********************************************************************************************************
 * @author : Hasitha Subhashana
 * @Description  :
 * ========================================================================================================
 * @History
 * --------------------------------------------------------------------------------------------------------
 * VERSION     AUTHOR                    DATE         DETAIL
    1.0    Hasitha Subhashana       23/12/2022   Initial implementation
 **********************************************************************************************************/

package com.hasithatest01.myapplication

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    lateinit var userImageView: ImageView
    private var userImageUri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        userImageView = findViewById(R.id.imageView)

        val nameTextView = findViewById<TextView>(R.id.NameView)
        val emailTextView = findViewById<TextView>(R.id.EmailView)
        val ageTextView = findViewById<TextView>(R.id.AgeView)
        val phoneNumTextView = findViewById<TextView>(R.id.PhoneNumView)

        var userObj = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable("userInfo", RegisterInfoActivity::class.java) as User?
        } else {
            intent.extras?.getParcelable("userInfo") as User?
        }

        if (userObj != null) {
            nameTextView.text = userObj.name
            emailTextView.text = userObj.email
            ageTextView.text = userObj.age
            phoneNumTextView.text = userObj.phoneNum

            userImageUri = Uri.parse( userObj.userImg)
            userImageView.setImageURI(userImageUri)
        }
    }
}