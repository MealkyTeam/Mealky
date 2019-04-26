package com.teammealky.mealky.presentation.addmeal.gallerycameradialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
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
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.teammealky.mealky.R
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.io.File

class GalleryCameraDialog : BaseDialogFragment<GalleryCameraPresenter, GalleryCameraPresenter.UI, GalleryCameraViewModel>(),
        GalleryCameraPresenter.UI, View.OnClickListener {

    override val vmClass = GalleryCameraViewModel::class.java
    private lateinit var easyImage: EasyImage

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
        easyImage = EasyImage.Builder(requireContext())
                .setCopyImagesToPublicGalleryFolder(false)
                .allowMultiple(false)
                .build()
    }

    override fun checkPermission() {
        PERMISSIONS.forEach { permission ->
            val result = ActivityCompat.checkSelfPermission(requireContext(), permission)
            if (result == PackageManager.PERMISSION_DENIED) {
                presenter?.hasPermission = false
                return
            }
        }

        presenter?.hasPermission = true
    }

    override fun showPermissionDialog() {
        Dexter.withActivity(activity).withPermissions(PERMISSIONS).withListener(BaseMultiplePermissionsListener())
                .withErrorListener { presenter?.errorOccurred() }
                .onSameThread()
                .check()
    }

    override fun showErrorToast() {
        Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }

    override fun openCamera() {
        easyImage.openCameraForImage(this)
    }

    override fun openGallery() {
        easyImage.openGallery(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.openCameraBtn -> presenter?.openCameraClicked()
            R.id.openGalleryBtn -> presenter?.openGalleryClicked()
        }
    }

    override fun passImageToAddMealFragment(file: File) {
        if (targetFragment is GalleryCameraListener) {
            (targetFragment as GalleryCameraListener).onInformationPassed(file)
        }

        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        easyImage.handleActivityResult(requestCode, resultCode, data, this.requireActivity(), object : DefaultCallback() {
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                presenter?.imageReceived(imageFiles[0].file)
            }

            override fun onImagePickerError(error: Throwable, source: MediaSource) {
                presenter?.errorOccurred()
            }
        })
    }

    interface GalleryCameraListener {
        fun onInformationPassed(file: File)
    }

    companion object {
        private val PERMISSIONS = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        )
    }
}