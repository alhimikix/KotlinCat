package xyz.myfur.animation

import java.awt.*

import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.collections.ArrayList
import kotlin.concurrent.timer


fun main() {
    Cat
    Window
}

object Window : JFrame("Cat") {

    init {

        isUndecorated = true

        defaultCloseOperation = EXIT_ON_CLOSE

        println("OK")
        setSize(600, 600)
        setLocationRelativeTo(null)
        add(CatPanel)
        background = Color(0,0,0,0)
        isVisible = true

        Cat.drawCycleCat()
    }


}

object Cat {

    var cats: ArrayList<BufferedImage> = ArrayList()

    init {
        loadCats()
    }

    private fun loadCats() {
        val cat = ImageIO.read(Cat.javaClass.getResourceAsStream("/cat/cat_small.png"))
        println(cat.width)
        println(cat.height)
        repeat(5) {
            val startX = it * cat.width/5
            val startY = 0

            val endX = (it + 1) * cat.width/5
            println("""$startX $endX""")

            cats.add(cat.getSubimage(startX, startY, cat.width/5, cat.width/5))

        }

        repeat(2) {
            val startX = it * cat.width/5
            val startY = cat.width/5

            val endX = (it + 1) * cat.width/5
            println("""$startX $endX""")
            cats.add(cat.getSubimage(startX, startY, cat.width/5, cat.width/5))
        }
    }

    var i = 0
        private set

    private lateinit var timer: Timer

    fun drawCycleCat() {
        timer = timer(period = 70) {
            if (i == 6) i = 0
            CatPanel.repaint()
            i++
        }
    }
}



object CatPanel : JPanel() {
    init {
        isVisible = true
        background = Color(0,0,0,0)
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        isDoubleBuffered = true
        isVisible = false
        isVisible = true
        g?.drawImage(Cat.cats[Cat.i], 0, 0, this)
    }
}
