/**********************************************************************************************************
 * @author : Hasitha Subhashana
 * @Description  :
 * ========================================================================================================
 * @History
 * --------------------------------------------------------------------------------------------------------
 * VERSION     AUTHOR                    DATE         DETAIL
    1.0    Hasitha Subhashana       23/11/2022   Initial implementation
 **********************************************************************************************************/
package com.hasithatest01.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val email: String,
    val age: String,
    val phoneNum: String,
    val userImg: String,
) : Parcelable