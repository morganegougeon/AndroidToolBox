package fr.isen.gougeon.androidtoolbox

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import kotlinx.android.synthetic.main.activity_data.*
import java.io.IOException


class DataActivity : AppCompatActivity() {

    val GALLERY = 1
    val CAMERA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        pictureButton.setOnClickListener() {
            showPictureDialog()
        }
    }


    fun showPictureDialog() {

        val pictureDialogBuilder = AlertDialog.Builder(this)

        pictureDialogBuilder.setNegativeButton("Fermer", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        val pictureDialogItems = arrayOf("Selectionner une photo depuis la gallerie", "Prendre une photo depuis la camera")
        pictureDialogBuilder.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }

        }

        val alert = pictureDialogBuilder.create()
        alert.setTitle("Veuillez faire un choix")
        alert.show()
    }

    fun choosePhotoFromGallary(){
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }
    fun takePhotoFromCamera(){}
}