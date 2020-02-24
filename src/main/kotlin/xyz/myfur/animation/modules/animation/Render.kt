package xyz.myfur.animation.modules.animation

import java.awt.AlphaComposite
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
        frame.setSize(animation.window.width,animation.window.height)
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
        animation?.layers?.forEach {layout ->
            (g as Graphics2D).let {g2 ->
                g2.color = Color(0,0,0,0)
                g2.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, layout.alpha)
                g2.drawImage(layout.images[this.currentImage], 0, 0, panel)
                g2.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)
            }
        }
    }
}