package com.example.teacherapp.utils

fun getRollno(email:String):String{

    val username= email.substringBefore("@")

    // Extract the numeric part and the "bct" part
    val prefix = username.take(3) // First three characters i.e078
    val stream = username.substring(3, 6).uppercase() // Next three characters, converted to uppercase
    val number = username.substring(6) // Remaining characters

    return "PUR${prefix}${stream}${number}"
}