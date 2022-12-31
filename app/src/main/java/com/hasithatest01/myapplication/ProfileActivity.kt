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

    lateinit var imageView2: ImageView
    private var imageUri2: Uri? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        imageView2 = findViewById(R.id.imageView)

        val textView1 = findViewById<TextView>(R.id.text_view_id1)
        val textView2 = findViewById<TextView>(R.id.text_view_id2)

        var obj = intent.extras?.getParcelable("message_key") as User?

        if (obj != null) {
            textView1.text = obj.Name
            textView2.text = obj.Email
            imageUri2 = Uri.parse( obj.ProfileImg)
            imageView2.setImageURI(imageUri2)
        }
    }
}