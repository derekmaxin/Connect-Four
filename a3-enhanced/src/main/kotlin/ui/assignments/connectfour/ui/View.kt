package ui.assignments.connectfour.ui

import javafx.animation.*
import javafx.event.EventHandler
import ui.assignments.connectfour.model.Model

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.util.Duration

class View() : VBox(), IView {
    override fun updateView(){
        println("in update")
    }

    //CONSTANTS
    val MYFONT = "Times New Roman"

    //High Level Rows
    var playerLabelRow = HBox();
    var startGameRow = HBox();
    var gameBoardRow = HBox();

    //player label row
    val player1Label = Label("Player #1").apply{
        textFill = Color.GREY
        font = Font(MYFONT, 30.0)
        padding = Insets(10.0)
    }
    val playerLabelBuffer = Label().apply{
        minWidth = 830.0
    }
    val player2Label = Label("Player #2").apply{
        textFill = Color.GREY
        font = Font(MYFONT, 30.0)
        padding = Insets(10.0)
    }
    var turnCounter = 0


    //player piece row
    val pieceRowBufferLabel = Label().apply{
        minWidth = 250.0
        minHeight = 100.0
    }
    val startGameInteractiveLabel = Label("Click here to start game!").apply{
        background = Background(BackgroundFill(Color.LIGHTGREEN, CornerRadii(0.0), null))
        minHeight = 75.0
        minWidth = 600.0
        font = Font(MYFONT, 30.0)
        alignment = Pos.CENTER
    }

    //game board row
    val gridPath = javaClass.getResource("/ui/assignments/connectfour/grid_8x7.png").toString()
    var gameBoard = ImageView(gridPath)
    val leftGameBoardBuffer = Label().apply{
        minWidth = 150.0
    }

    init {
        //player label row
        playerLabelRow.children.add(player1Label)
        playerLabelRow.children.add(playerLabelBuffer)
        playerLabelRow.children.add(player2Label)

        Model.onPieceDropped.addListener { _, _, new ->
            if (new.toString() == "2") {
                player1Label.textFill = Color.BLACK
                player2Label.textFill = Color.GREY
            } else if (new.toString() == "1") {
                player1Label.textFill = Color.GREY
                player2Label.textFill = Color.BLACK
            }
            turnCounter++
            playerLabelBuffer.text = "Pieces dropped: $turnCounter"
            playerLabelBuffer.font = Font(MYFONT, 30.0)
            playerLabelBuffer.alignment = Pos.CENTER
        }


        //player piece row
        startGameInteractiveLabel.onMouseClicked = EventHandler{
            startGame()
            Model.startGame() //move from setup stage to game loop stage

        }

        startGameRow.children.add(pieceRowBufferLabel)
        startGameRow.children.add(startGameInteractiveLabel)


        //game board row
        gameBoardRow.children.add(leftGameBoardBuffer)
        gameBoardRow.children.add(gameBoard)

        //added to put end game message over top
        val endStack = StackPane()
        endStack.children.add(gameBoardRow)

        //GAME RESOLUTION
        var endGameLabel = Label().apply{
            minWidth = 500.0
            minHeight = 300.0
        }

        Model.onGameWin.addListener { _, _, new ->
            endGameLabel.font = Font(MYFONT, 42.0)
            endGameLabel.alignment = Pos.CENTER
            endGameLabel.text = "Player #${new.toString()} won!!!"
            endGameLabel.background = Background(BackgroundFill(Color.LIGHTGREEN, CornerRadii(0.0), null))
            player1Label.textFill = Color.GREY
            player2Label.textFill = Color.GREY
        }
        Model.onGameDraw.addListener { _, _, new ->
            endGameLabel.font = Font(MYFONT, 42.0)
            endGameLabel.alignment = Pos.CENTER
            endGameLabel.text = "Draw"
            endGameLabel.background = Background(BackgroundFill(Color.LIGHTGREY, CornerRadii(0.0), null))
            player1Label.textFill = Color.GREY
            player2Label.textFill = Color.GREY
        }

        endStack.children.add(endGameLabel)

        children.add(playerLabelRow)
        children.add(startGameRow)
        children.add(endStack)
    }

    fun startGame() {
        player1Label.textFill = Color.BLACK

        children.remove(startGameRow)

        var playColNum = -1
        var curTurn = "1"

        //make the pieces draggable
        data class DragInfo(
            var target: ImageView? = null,
            var anchorX: Double = 0.0,
            var anchorY: Double = 0.0,
            var initialX: Double = 0.0,
            var initialY: Double = 0.0
        )

        var dragInfo = DragInfo()
        //

        //replace startGameRow with playerPieceRow

        var playerPieceRow = HBox();
        var redPieceStackPane = StackPane()
        var yellowPieceStackPane = StackPane()

        val redPiecePath = javaClass.getResource("/ui/assignments/connectfour/piece_red.png").toString()
        val yellowPiecePath = javaClass.getResource("/ui/assignments/connectfour/piece_yellow.png").toString()


        fun createRedPiece(): Node {
            return ImageView(redPiecePath).apply {

                //dragability
                addEventFilter(MouseEvent.MOUSE_PRESSED) {
                    dragInfo = DragInfo(
                        this, it.sceneX, it.sceneY,
                        translateX, translateY
                    )
                }
                addEventFilter(MouseEvent.MOUSE_DRAGGED) {
                    var translatedValue = dragInfo.initialX + it.sceneX - dragInfo.anchorX
                    if (translatedValue >= 100.0 && translatedValue < 200.0) { //snap to above drops
                        translateX = 150.0
                        playColNum = 0
                    } else if (translatedValue >= 200.0 && translatedValue < 300.0) {
                        translateX = 250.0
                        playColNum = 1
                    } else if (translatedValue >= 300.0 && translatedValue < 400.0) {
                        translateX = 350.0
                        playColNum = 2
                    } else if (translatedValue >= 400.0 && translatedValue < 500.0) {
                        translateX = 450.0
                        playColNum = 3
                    } else if (translatedValue >= 500.0 && translatedValue < 600.0) {
                        translateX = 550.0
                        playColNum = 4
                    } else if (translatedValue >= 600.0 && translatedValue < 700.0) {
                        translateX = 650.0
                        playColNum = 5
                    } else if (translatedValue >= 700.0 && translatedValue < 800.0) {
                        translateX = 750.0
                        playColNum = 6
                    } else if (translatedValue >= 800.0 && translatedValue < 900.0) {
                        translateX = 850.0
                        playColNum = 7
                    } else { //not over a drop
                        if (it.sceneX < 1050.0 && it.sceneX > 50.0) { //restrict dragable area
                            translateX = dragInfo.initialX + it.sceneX - dragInfo.anchorX
                            playColNum = -1
                        }
                    }


                    if (it.sceneY < 110.0 && it.sceneY > 75.0) { //restrict dragable area
                        translateY = dragInfo.initialY + it.sceneY - dragInfo.anchorY
                    }
                }
                addEventFilter(MouseEvent.MOUSE_RELEASED) {
                    dragInfo = DragInfo()

                    //if not over a column (playNumCol == -1), animate back to starting position,
                    // else try to play the piece
                    if (playColNum == -1) {
                        val animation = TranslateTransition(
                            Duration.millis(750.0),
                            this
                        ).apply {
                            toY = 10.0
                            toX = 10.0
                        }
                        animation.play()
                    } else {
                        Model.dropPiece(playColNum) //listen to onPieceDropped to get result
                    }
                }
            }
        }

        fun createYellowPiece(): Node {
            return ImageView(yellowPiecePath).apply {
                //dragability
                addEventFilter(MouseEvent.MOUSE_PRESSED) {
                    dragInfo = DragInfo(
                        this, it.sceneX, it.sceneY,
                        translateX, translateY
                    )
                }
                addEventFilter(MouseEvent.MOUSE_DRAGGED) {
                    var translatedValue = dragInfo.initialX + it.sceneX - dragInfo.anchorX
                    if (translatedValue >= -200.0 && translatedValue < -100.0) { //snap to above drops
                        translateX = -150.0
                        playColNum = 7
                    } else if (translatedValue >= -300.0 && translatedValue < -200.0) {
                        translateX = -250.0
                        playColNum = 6
                    } else if (translatedValue >= -400.0 && translatedValue < -300.0) {
                        translateX = -350.0
                        playColNum = 5
                    } else if (translatedValue >= -500.0 && translatedValue < -400.0) {
                        translateX = -450.0
                        playColNum = 4
                    } else if (translatedValue >= -600.0 && translatedValue < -500.0) {
                        translateX = -550.0
                        playColNum = 3
                    } else if (translatedValue >= -700.0 && translatedValue < -600.0) {
                        translateX = -650.0
                        playColNum = 2
                    } else if (translatedValue >= -800.0 && translatedValue < -700.0) {
                        translateX = -750.0
                        playColNum = 1
                    } else if (translatedValue >= -900.0 && translatedValue < -800.0) {
                        translateX = -850.0
                        playColNum = 0
                    } else { //not over a drop
                        if (it.sceneX < 1050.0 && it.sceneX > 50.0) { //restrict dragable area
                            translateX = dragInfo.initialX + it.sceneX - dragInfo.anchorX
                            playColNum = -1
                        }
                    }


                    if (it.sceneY < 110.0 && it.sceneY > 75.0) { //restrict dragable area
                        translateY = dragInfo.initialY + it.sceneY - dragInfo.anchorY
                    }
                }
                addEventFilter(MouseEvent.MOUSE_RELEASED) {
                    dragInfo = DragInfo()

                    //if not over a column (playNumCol == -1), animate back to starting position,
                    // else try to play the piece
                    if (playColNum == -1) {
                        val animation = TranslateTransition(
                            Duration.millis(750.0),
                            this
                        ).apply {
                            toY = 0.0
                            toX = 0.0
                        }
                        animation.play()
                    } else {
                        Model.dropPiece(playColNum) //listen to onPieceDropped to get result
                    }
                }
            }
        }

        var redPiece: Node? = null;
        var yellowPiece: Node? = null;

        Model.onPieceDropped.addListener { _, _, new ->
            println("On piece dropped: $new")
            if (new.toString() == "1") { //assuming new.toString is 1 or 2 representing the player
                //play red piece
                val animation = TranslateTransition(
                    Duration.millis(200.0),
                    redPiece
                ).apply {
                    toX = 147.0 + (playColNum * 100)
                    if (new != null) { //compiler wants null check to access piece.y
                        toY = 100.0 + (new.y * 100)
                        println("new.y: ${new.y}")
                    }
                }
                animation.play()
            } else if (new.toString() == "2") {
                //play yellow piece
                val animation = TranslateTransition(
                    Duration.millis(500.0),
                    yellowPiece
                ).apply {
                    toX = -143.0 + ((7 - playColNum) * -100)
                    if (new != null) { //compiler wants null check to access piece.y
                        toY = 100.0 + (new.y * 100)
                        println("new.y: ${new.y}")
                    }
                }
                animation.play()
            } else if (new == null) { //column was full
                println("column is full")
                if (curTurn == "1") {
                    val animation = TranslateTransition(
                        Duration.millis(750.0),
                        redPiece
                    ).apply {
                        toY = 10.0
                        toX = 10.0
                    }
                    animation.play()
                } else if (curTurn == "2"){
                    val animation = TranslateTransition(
                        Duration.millis(750.0),
                        yellowPiece
                    ).apply {
                        toY = 0.0
                        toX = 0.0
                    }
                    animation.play()
                }
            }

        }

        //make onNextPlayer listener that generates a red piece or yellow piece for whomever's turn
        Model.onNextPlayer.addListener { _, _, new ->
            if (new.toString() == "1") {
                curTurn = "1"
                redPiece = createRedPiece()
                //playerPieceRow.children.add(1, redPiece)
                redPieceStackPane.children.add(redPiece)

            } else if (new.toString() == "2") {
                curTurn = "2"
                yellowPiece = createYellowPiece()
                //playerPieceRow.children.add(3, yellowPiece)
                yellowPieceStackPane.children.add(yellowPiece)
            }
        }

        val leftPieceBuffer = Label().apply {
            minWidth = 15.0
        }
        val pieceBuffer = Label().apply {
            minHeight = 100.0
            minWidth = 905.0
        }

        playerPieceRow.children.add(leftPieceBuffer)
        playerPieceRow.children.add(redPieceStackPane)
        playerPieceRow.children.add(pieceBuffer)
        playerPieceRow.children.add(yellowPieceStackPane)

        children.add(1, playerPieceRow)
    }
}