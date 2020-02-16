package xyz.myfur.animation

import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JPanel

class Animation(
        var name: String,
        var blocks: Int,
        var block_width: Int,
        var block_height: Int,
        var pause: Long
)
{
    var layers:ArrayList<Layer> = ArrayList()


}

class Layer(
        var alpha: Float,
        val animation:Animation,
        private var file_name: String
){
    var images: ArrayList<BufferedImage> = ArrayList()


    init {
        load()
    }

    private fun load() {
        val sprite = ImageIO.read(Animation::class.java.getResourceAsStream(file_name))
        var i = 0

        loop@ for (a in 0 until sprite.height / (animation.block_height)) {
            for (b in 0 until sprite.width / (animation.block_width)) {
                if (i++ >= animation.blocks)
                    break@loop
                val startX = (b) * animation.block_width
                val startY = sprite.width / 5 * a
                images.add(sprite.getSubimage(startX, startY, animation.block_width, animation.block_height))
            }
        }
    }

    fun paint(g:Graphics2D,panel:JPanel,render: Render){
        g.apply {
            color = Color(0,0,0,0)
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
            drawImage(images[render.currentImage], 0, 0, panel)
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)
        }
    }
}