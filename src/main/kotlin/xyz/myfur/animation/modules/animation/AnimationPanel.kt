package xyz.myfur.animation.modules.animation

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class AnimationPanel(var render: Render) : JPanel()
{
    init {
        isVisible = true
        background = Color(0, 0, 0, 0)
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        isDoubleBuffered = true
        isVisible = false
        isVisible = true
        render.paint(g,this)
    }
}