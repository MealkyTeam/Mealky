package com.teammealky.mealky.presentation.addmeal.gallerycameradialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.teammealky.mealky.presentation.App
import com.teammealky.mealky.presentation.commons.presenter.BaseDialogFragment
import kotlinx.android.synthetic.main.gallery_camera_dialog.*
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.teammealky.mealky.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class GalleryCameraDialog : BaseDialogFragment<GalleryCameraPresenter, GalleryCameraPresenter.UI, GalleryCameraViewModel>(),
        GalleryCameraPresenter.UI, View.OnClickListener {

    override val vmClass = GalleryCameraViewModel::class.java
    private var currentPhotoPath: String = ""

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_camera_dialog, null)
        return AlertDialog.Builder(context)
                .setView(view)
                .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.get(requireContext()).getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        dialog.openCameraBtn.setOnClickListener(this)
        dialog.openGalleryBtn.setOnClickListener(this)
    }

    override fun checkPermission() {
        if (context == null) return
        val result = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_DENIED) {
            presenter?.hasPermission = false
            return
        }

        presenter?.hasPermission = true
    }

    override fun showPermissionDialog() {
        Dexter.withActivity(activity).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(BaseMultiplePermissionsListener())
                .withErrorListener { presenter?.errorOccurred() }
                .onSameThread()
                .check()
    }

    override fun showErrorToast() {
        Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }

    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                ?: return null
        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        ).apply {
            currentPhotoPath = "file://$absolutePath"
        }
    }

    override fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    presenter?.errorOccurred()
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(context!!, getString(R.string.fileProviderAuthorities), it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_GALLERY_PHOTO)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.openCameraBtn -> presenter?.openCameraClicked()
            R.id.openGalleryBtn -> presenter?.openGalleryClicked()
        }
    }

    override fun passImageToAddMealFragment(photoPath: String) {
        if (targetFragment is GalleryCameraDialog.GalleryCameraListener) {
            (targetFragment as GalleryCameraDialog.GalleryCameraListener).onInformationPassed(currentPhotoPath)
        }

        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                presenter?.imageReceived(currentPhotoPath)
            }
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                val selectedImageUri = data?.data
                currentPhotoPath = selectedImageUri.toString()
                presenter?.imageReceived(currentPhotoPath)
            }
        }
    }

    interface GalleryCameraListener {
        fun onInformationPassed(imagePath: String)
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 101
        const val REQUEST_GALLERY_PHOTO = 102
    }
}