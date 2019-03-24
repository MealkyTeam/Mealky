package com.teammealky.mealky.presentation.addmeal.gallerycameradialog

import com.teammealky.mealky.presentation.commons.presenter.BasePresenter
import com.teammealky.mealky.presentation.commons.presenter.BaseUI
import javax.inject.Inject

class GalleryCameraPresenter @Inject constructor() : BasePresenter<GalleryCameraPresenter.UI>() {
    var hasPermission = false

    fun openGalleryClicked() {
        ui().perform {
            it.checkPermission()
            if (!hasPermission) {
                it.showPermissionDialog()
                return@perform
            }
            it.openGallery()
        }
    }

    fun openCameraClicked() {
        ui().perform {
            it.checkPermission()
            if (!hasPermission) {
                it.showPermissionDialog()
                return@perform
            }

            it.openCamera()
        }
    }

    fun goToSettingsClicked() {
        ui().perform { it.openSettings() }
    }

    fun permissionDenied() {
        ui().perform { it.showSettingsDialog() }
    }

    fun errorOccurred() {
        ui().perform { it.showErrorToast() }
    }

    fun imageReceived(photoPath: String) {
        ui().perform { it.passImageToAddMealFragment(photoPath) }
    }

    interface UI : BaseUI {
        fun openCamera()
        fun openGallery()
        fun openSettings()
        fun showPermissionDialog()
        fun checkPermission()
        fun showSettingsDialog()
        fun showErrorToast()
        fun showNoSpaceToast()
        fun passImageToAddMealFragment(photoPath: String)
    }
}