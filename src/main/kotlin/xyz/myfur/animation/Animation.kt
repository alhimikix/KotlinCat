package xyz.myfur.animation

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Animation(
        var name: String,
        var blocks: Int,
        var block_width: Int,
        var block_height: Int,
        var pause: Long,
        private var file_name: String
)
{
    var images: ArrayList<BufferedImage> = ArrayList()


    init {
        load()
    }

    private fun load() {
        val sprite = ImageIO.read(Animation::class.java.getResourceAsStream(file_name))
        var i = 0

        loop@ for (a in 0 until sprite.height / (block_height)) {
            for (b in 0 until sprite.width / (block_width)) {
                if (i++ >= blocks)
                    break@loop
                val startX = (b) * block_width
                val startY = sprite.width / 5 * a
                images.add(sprite.getSubimage(startX, startY, block_width, block_height))
            }
        }
    }

    override fun toString(): String {
        return "Animation(name='$name', blocks=$blocks, block_width=$block_width, block_height=$block_height, pause=$pause, file_name='$file_name', images=$images)"
    }


}