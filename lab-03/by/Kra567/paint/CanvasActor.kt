package by.Kra567.paint

import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import kotlin.math.max
import kotlin.math.min


interface PenEnvironment {
    fun getPenThickness() : Double
    fun getPenColor() : Color

}
interface CanvasActor {
    fun act(context : GraphicsContext, event : MouseEvent) : Unit
}

class LineActor : CanvasActor{
    private var prevPos : Pair<Double,Double>? = null
    override fun act(context: GraphicsContext, event: MouseEvent) {
        if (event.eventType == MouseEvent.MOUSE_MOVED){
            prevPos = Pair(event.x,event.y)
        }
        if (event.eventType == MouseEvent.MOUSE_DRAGGED){

            prevPos?.let{ context.strokeLine(it.first,it.second,event.x,event.y)}
            prevPos = Pair(event.x,event.y)
        }
    }
}

typealias RectShapeDrawer = (Double,Double,Double,Double,GraphicsContext) -> Unit

fun DrawRect(beginX : Double, beginY : Double,endX : Double,endY : Double, context: GraphicsContext) : Unit{
    context.strokeRect(
            min(beginX,endX),
            min(beginY,endY),
            max(beginX,endX) - min(beginX,endX),
            max(beginY,endY) - min(beginY,endY))
}

fun DrawOval(beginX: Double,beginY: Double,endX :Double,endY : Double,context: GraphicsContext){
    context.strokeOval(min(beginX,endX),
            min(beginY,endY),
            max(beginX,endX) - min(beginX,endX),
            max(beginY,endY) - min(beginY,endY))
}

class ShapeActor(private val gen : RectShapeDrawer) : CanvasActor {
    private var begin : Pair<Double,Double>? = null
    override fun act(context: GraphicsContext, event: MouseEvent){
        if (event.eventType == MouseEvent.MOUSE_DRAGGED){

            begin = begin ?: Pair(event.x,event.y)

        }
        if (event.eventType == MouseEvent.MOUSE_RELEASED){
            begin?.let {
                gen(it.first,it.second,event.x,event.y,context)
            }
            begin = null
        }
    }
}
