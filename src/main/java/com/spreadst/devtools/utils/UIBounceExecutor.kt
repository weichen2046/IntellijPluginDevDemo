package com.spreadst.devtools.utils

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

/**
 * Only use in swing dispatch thread.
 */
class UIBounceExecutor(delay: Int) : ActionListener {
    private var runnable: Runnable? = null
    private val timer: Timer = Timer(delay, this)

    init {
        timer.isRepeats = false
    }

    fun execute(runnable: Runnable) {
        val old = this.runnable
        this.runnable = runnable
        if (old == null) {
            timer.start()
        } else {
            timer.restart()
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        this.runnable?.run()
    }
}