package com.kuba.mealky.presentation.commons.presenter

import java.util.*

class UICommandExecutor<UI> : SafeUI<UI> {

    private var ui: UI? = null
    private val commandQueue: Queue<(UI) -> Unit> = LinkedList()
    private var stickyCommand: ((UI) -> Unit)? = null

    fun attach(ui: UI) {
        this.ui = ui
        executePending()
    }

    fun detach() {
        this.ui = null
    }

    override fun perform(command: (UI) -> Unit) {
        val currentUI = this.ui
        if (currentUI != null) command(currentUI) else commandQueue.add(command)
    }

    override fun performSticky(command: (UI) -> Unit) {
        val currentUI = this.ui
        this.stickyCommand = command
        if (currentUI != null) command(currentUI)
    }

    private fun executePending() {
        val currentUI = this.ui
        if (currentUI != null) {
            var cmd = commandQueue.poll()
            while (cmd != null) {
                cmd(currentUI)
                cmd = commandQueue.poll()
            }
            stickyCommand?.invoke(currentUI)
        }
    }

}