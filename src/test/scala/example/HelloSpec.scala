package example

import org.scalatest._
import info.folone.scala.poi._

class HelloSpec extends FlatSpec with Matchers {
  "The addSheetToWorkbook function" should "preserve workbook's sheet order" in {
    val workbook = Workbook { Set(
                               Sheet("name") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                               Sheet("name2") { Set(Row(0) { Set(StringCell(0, "data")) }) }
                             )
    }
    val newSheet = Sheet("name3") { Set( Row(0) { Set(StringCell(0, "data")) } ) }
    Hello.addSheetToWorkbook(workbook, newSheet) shouldEqual Workbook {
      Set(Sheet("name") { Set(Row(0) { Set(StringCell(0, "data")) }) },
          Sheet("name2") { Set(Row(0) { Set(StringCell(0, "data")) }) },
          Sheet("name3") { Set(Row(0) { Set(StringCell(0, "data")) }) }
      )
    }
  }
}
