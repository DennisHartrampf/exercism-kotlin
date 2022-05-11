import org.junit.Test
import kotlin.test.assertEquals

class MinesweeperBoardTest {

    @Test
    fun testInputBoardWithNoRowsAndNoColumns() {
        val inputBoard = emptyList<String>()
        val expectedNumberedBoard = emptyList<String>()
        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithOneRowAndNoColumns() {
        val inputBoard = listOf("")
        val expectedNumberedBoard = listOf("")
        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithNoMines() {
        val inputBoard = listOf(
                "   ",
                "   ",
                "   "
        )

        val expectedNumberedBoard = listOf(
                "   ",
                "   ",
                "   "
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithOnlyMines() {
        val inputBoard = listOf(
                "***",
                "***",
                "***"
        )

        val expectedNumberedBoard = listOf(
                "***",
                "***",
                "***"
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithSingleMineAtCenter() {
        val inputBoard = listOf(
                "   ",
                " * ",
                "   "
        )

        val expectedNumberedBoard = listOf(
                "111",
                "1*1",
                "111"
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithMinesAroundPerimeter() {
        val inputBoard = listOf(
                "***",
                "* *",
                "***"
        )

        val expectedNumberedBoard = listOf(
                "***",
                "*8*",
                "***"
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithSingleRowAndTwoMines() {
        val inputBoard = listOf(" * * ")

        val expectedNumberedBoard = listOf("1*2*1")

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithSingleRowAndTwoMinesAtEdges() {
        val inputBoard = listOf("*   *")

        val expectedNumberedBoard = listOf("*1 1*")

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithSingleColumnAndTwoMines() {
        val inputBoard = listOf(
                " ",
                "*",
                " ",
                "*",
                " "
        )

        val expectedNumberedBoard = listOf(
                "1",
                "*",
                "2",
                "*",
                "1"
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithSingleColumnAndTwoMinesAtEdges() {
        val inputBoard = listOf(
                "*",
                " ",
                " ",
                " ",
                "*"
        )

        val expectedNumberedBoard = listOf(
                "*",
                "1",
                " ",
                "1",
                "*"
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testInputBoardWithMinesInCross() {
        val inputBoard = listOf(
                "  *  ",
                "  *  ",
                "*****",
                "  *  ",
                "  *  "
        )

        val expectedNumberedBoard = listOf(
                " 2*2 ",
                "25*52",
                "*****",
                "25*52",
                " 2*2 "
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

    @Test
    fun testLargeInputBoard() {
        val inputBoard = listOf(
                " *  * ",
                "  *   ",
                "    * ",
                "   * *",
                " *  * ",
                "      "
        )

        val expectedNumberedBoard = listOf(
                "1*22*1",
                "12*322",
                " 123*2",
                "112*4*",
                "1*22*2",
                "111111"
        )

        val actualNumberedBoard = MinesweeperBoard(inputBoard).withNumbers()

        assertEquals(expectedNumberedBoard, actualNumberedBoard)
    }

}
