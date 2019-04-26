package com.teammealky.mealky.presenters

import com.teammealky.mealky.presentation.addmeal.gallerycameradialog.GalleryCameraPresenter
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test
import java.io.File

class GalleryCameraPresenterTest {
    private val view = mockk<GalleryCameraPresenter.UI>()
    private var presenter = GalleryCameraPresenter()

    @Before
    fun setUp() {
        every { view.openCamera() } just Runs
        every { view.openGallery() } just Runs
        every { view.showPermissionDialog() } just Runs
        every { view.checkPermission() } just Runs
        every { view.showErrorToast() } just Runs
        every { view.passImageToAddMealFragment(any()) } just Runs
    }


    /**
     * Scenario: Open camera when there is permission for storage.
     * Given new created presenter with permission
     * When I click on openCamera button
     * Then open camera.
     */
    @Test
    fun `Open camera when there is permission for storage`() {
        //Given
        presenter.attach(view)
        presenter.hasPermission = true
        //When
        presenter.openCameraClicked()

        //Then
        verifySequence {
            view.checkPermission()
            view.openCamera()
        }
    }

    /**
     * Scenario: Open gallery when there is permission for storage.
     * Given new created presenter with permission
     * When I click on openGallery button
     * Then open gallery.
     */
    @Test
    fun `Open gallery when there is permission for storage`() {
        //Given
        presenter.attach(view)
        presenter.hasPermission = true
        //When
        presenter.openGalleryClicked()

        //Then
        verifySequence {
            view.checkPermission()
            view.openGallery()
        }
    }

    /**
     * Scenario: Don't open camera when there is no permission for it.
     * Given new created presenter without permission
     * When I click on openCamera button
     * Then do nothing.
     */
    @Test
    fun `Don't open camera when there is no permission for it`() {
        //Given
        presenter.attach(view)
        presenter.hasPermission = false
        //When
        presenter.openCameraClicked()

        //Then
        verifySequence {
            view.checkPermission()
            view.showPermissionDialog()
        }
    }

    /**
     * Scenario: Don't open gallery when there is no permission for it.
     * Given new created presenter without permission
     * When I click on openGallery button
     * Then do nothing.
     */
    @Test
    fun `Don't open gallery when there is no permission for it`() {
        //Given
        presenter.attach(view)
        presenter.hasPermission = false
        //When
        presenter.openGalleryClicked()

        //Then
        verifySequence {
            view.checkPermission()
            view.showPermissionDialog()
        }
    }

    /**
     * Scenario: When user took a photo or chose it from gallery hide dialog and pass image to fragment.
     * Given new created presenter
     * When user have chosen an image
     * Then finish dialog and pass image to fragment.
     */
    @Test
    fun `Pass image to fragment and dismiss`() {
        //Given
        presenter.attach(view)
        presenter.hasPermission = true
        val image = File("imagePath")
        //When
        presenter.imageReceived(image)

        //Then
        verifySequence {
            view.passImageToAddMealFragment(image)
        }
    }
}