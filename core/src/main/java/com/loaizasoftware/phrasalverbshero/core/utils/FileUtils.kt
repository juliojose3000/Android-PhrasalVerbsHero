package com.loaizasoftware.phrasalverbshero.core.utils

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FileUtils {

    companion object {

        suspend fun getImageUrl(fileName: String): String {
            return try {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val folder =
                    fileName.first() //Each image is inside a folder based on the first letter of the image name
                val imageRef = storageRef.child("$folder/${fileName}.png")
                imageRef.downloadUrl.await().toString()
            } catch (e: Exception) {
                e.printStackTrace()
                // Load the default image if the specific one is not found
                val defaultImageRef = FirebaseStorage.getInstance().reference.child("default_image.png")
                try {
                    defaultImageRef.downloadUrl.await().toString()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    "" // Return an empty string or a local placeholder if even the default image fails
                }
            }
        }

    }

}