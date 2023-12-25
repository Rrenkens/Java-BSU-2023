package by.Kra567.paint

import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.StrokeLineJoin

class ActedCanvas(w : Double,h : Double,enver : () -> PenEnvironment,vararg actors : CanvasActor) : Canvas(w,h) {
    private var actorList : List<CanvasActor> = actors.toList()
    private var currentActor : Int = 0
    val actorAmount : Int = actorList.size
    /*
    override fun isResizable(): Boolean {
        return true
    }

    override fun resize(w: Double, h: Double) {
        width = w
        height = h
    }

    override fun maxWidth(p0: Double): Double {
        return Double.POSITIVE_INFINITY
    }

    override fun maxHeight(p0: Double): Double {
        return Double.POSITIVE_INFINITY
    }

     */
    init{

        addEventHandler(MouseEvent.ANY, EventHandler {
            var actor = actorList.get(currentActor)
            var env = enver()
            var ctx = graphicsContext2D
            ctx.restore()
            ctx.lineWidth = env.getPenThickness()
            ctx.stroke = env.getPenColor()
            actor.act(ctx,it)
            })
        }
    fun switch(actor : Int){
        currentActor = actor
    }


}