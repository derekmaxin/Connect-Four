package ui.assignments.connectfour

import ui.assignments.connectfour.ui.View
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
//import ui.assignments.connectfour.ui.IView

class ConnectFourApp : Application() {
    val view = View()
    override fun start(stage: Stage) {
        val scene = Scene(view, 1100.0, 900.0)
        stage.title = "CS349 - A3 Connect Four - dmaxin"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}