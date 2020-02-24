package xyz.myfur.animation.modules.animation

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.concurrent.thread

data class Animation(
        val blocks: Int,
        val layers: List<Layer>,
        val name: String,
        val pause: Long,
        val window: Window
)

data class Block(
        val height: Int,
        val width: Int
)

data class Layer(
        val alpha: Float,
        val block: Block,
        val blocks: Int,
        val file_name: String,
        val offset: Offset
){
    var images: ArrayList<BufferedImage> = ArrayList()

    init {

        thread() {
            val sprite = ImageIO.read(AnimationLoader.loadImage(file_name))
            var i = 0

            loop@ for (a in 0 until sprite.height / (block.height)) {
                for (b in 0 until sprite.width / (block.width)) {
                    if (i++ >= blocks)
                        break@loop
                    val startX = (b) * block.width
                    val startY = sprite.width / 5 * a
                    images.add(sprite.getSubimage(startX, startY, block.width, block.height))
                }
            }
            println("Layer $file_name")
        }
    }
}

data class Offset(
        val x: Int,
        val y: Int
)

data class Window(
        val height: Int,
        val width: Int
)