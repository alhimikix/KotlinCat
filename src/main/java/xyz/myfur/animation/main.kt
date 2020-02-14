package xyz.myfur.animation

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.awt.*

import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.concurrent.timer


fun main() {
    val render = Render()

    thread(name = "Console") {
        while (true){
            println("Enter animation name")
            val sc = Scanner(System.`in`)
            val name = sc.nextLine().trim()
            val anim = AnimationLoader.animations.firstOrNull { it.name == name }
            if (anim!=null){
                render.render(anim)
            }else{
                println("Animation found!!")
            }
        }
    }

}



object AnimationLoader{

    var animations:ArrayList<Animation> = ArrayList()
    private set
    fun loadAnimations() {
        animations = ArrayList()
        JSON.parseArray(AnimationLoader.javaClass.getResourceAsStream("/animations.json").bufferedReader().readText())
        .forEach{
            (it as JSONObject).apply {
                animations.add(Animation(
                        getString("name"),
                        getInteger("blocks"),
                        getInteger("block_width"),
                        getInteger("block_height"),
                        getLong("pause"),
                        getString("file_name"))
                )
            }
        }
    }

    fun loadAnimationsIfNotLoaded() {
        if(animations.size==0) loadAnimations()
    }

}

class Render
{
    private val frame = JFrame("(=^-^=)")
    private var animation: Animation? = null
    private var timer:Timer? = null
    private var imagePanel:AnimationPanel? = null

    init {
        AnimationLoader.loadAnimationsIfNotLoaded()
        frame.isUndecorated = true
        frame.background = Color(0,0,0,0)
        frame.isAlwaysOnTop = true
    }

    private var currentImage = 0

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

    fun paint(g: Graphics,panel:JPanel) {
        g.drawImage(this.animation?.images?.get(this.currentImage), 0, 0, panel)
    }

}

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


class AnimationPanel(var render:Render) : JPanel()
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
