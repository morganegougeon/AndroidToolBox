package fr.isen.gougeon.androidtoolbox

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_data.*


class DataActivity : AppCompatActivity(), LocationListener {

    val GALLERY = 1
    val CAMERA = 2
    val PERMISSION_CODE = 1000
    val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var locationManager: LocationManager





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        requestPermission(android.Manifest.permission.READ_CONTACTS, PERMISSION_CODE){readContacts()}

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        pictureButton.setOnClickListener() {
            showPictureDialog()
        }
        requestPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSION_CODE){startGPS()}
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
    fun takePhotoFromCamera(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                //permission was not enabled
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //show popup to request permission
                requestPermissions(permission, PERMISSION_CODE)
            }
            else{
                //permission already granted
                openCamera()
            }
        }
        else{
            //system os is < marshmallow
            openCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == GALLERY)
            {
                pictureButton.setImageURI(data?.data)
            }
            if(requestCode == CAMERA)
            {
                pictureButton.setImageURI(image_uri)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, CAMERA)

    }

    fun requestPermission(permission: String, requestCode: Int, handler: () -> Unit)
    {

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(
                    this,
                    "Merci d'accepter les permissions dans vos parametres",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        } else {
            handler()
        }
    }

    fun readContacts(){
        val contactList = ArrayList<ContactModel>()
        val contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while(contacts?.moveToNext() ?: false)
        {
            val displayName = contacts?.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
            val contactModel = ContactModel()
            contactModel.displayName = displayName.toString()
            contactList.add(contactModel)
        }
        contactRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contactRecyclerView.adapter = ContactsAdapter(contactList)
    }

    @SuppressLint("MissingPermission")
    fun startGPS(){
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let{
            refreshPosition(it)
        }
    }

    fun refreshPosition(location: Location){
        latitudeTextView.text="Latitude : ${location.latitude}"
        longitudeTextView.text="Longitude : ${location.longitude}"

    }

    override fun onLocationChanged(location: Location?) {
        location?.let{
            refreshPosition(it)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}