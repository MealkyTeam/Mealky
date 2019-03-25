package com.teammealky.mealky.presenters

import com.teammealky.mealky.presentation.addmeal.gallerycameradialog.GalleryCameraPresenter
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.Before

class GalleryCameraPresenterTest {
    private val view = mockk<GalleryCameraPresenter.UI>()
    private lateinit var presenter: GalleryCameraPresenter

    @Before
    fun setUp() {
        every { view.openCamera() } just Runs
        every { view.openGallery() } just Runs
        every { view.openSettings() } just Runs
        every { view.showPermissionDialog() } just Runs
        every { view.checkPermission() } just Runs
        every { view.showSettingsDialog() } just Runs
        every { view.showErrorToast() } just Runs
        every { view.showNoSpaceToast() } just Runs
        every { view.passImageToAddMealFragment(any()) } just Runs
    }


}