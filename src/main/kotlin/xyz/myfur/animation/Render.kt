package xyz.myfur.animation

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.concurrent.timer


class Render
{
    private val frame = JFrame("(=^-^=)")
    private var animation: Animation? = null
    private var timer: Timer? = null
    private var imagePanel: AnimationPanel? = null

    init {
        AnimationLoader.loadAnimationsIfNotLoaded()
        frame.isUndecorated = true
        frame.background = Color(0,0,0,0)
        frame.isAlwaysOnTop = true
    }

    var currentImage = 0

    fun render(animation: Animation) {
        if (this.animation != null) stopRender()
        this.animation = animation
        imagePanel = AnimationPanel(this)
        frame.setSize(animation.block_width,animation.block_height)
        frame.add(imagePanel)
        frame.setLocationRelativeTo(null)
        this.timer = timer(period = animation.pause){
            if (currentImage == animation.blocks-1) currentImage = 0
            imagePanel!!.repaint()
            currentImage++
        }
        frame.isVisible = true
    }

    fun stopRender(){
        frame.isVisible = false
        timer?.cancel()
        this.animation = null
        this.timer = null
        currentImage = 0
    }

    fun paint(g: Graphics, panel: JPanel) {
        animation?.layers?.forEach {
            it.paint(g as Graphics2D, panel, this)
        }
    }

}