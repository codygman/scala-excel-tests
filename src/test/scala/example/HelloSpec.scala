package example

import org.scalatest._
import info.folone.scala.poi._
import scala.collection.immutable.ListSet
import scalaz._

class HelloSpec extends FlatSpec with Matchers {
  "The addSheetToWorkbook function" should "preserve workbook's sheet order" in {
    val workbook = Workbook { ListSet(
                               List(
                                 Sheet("name1") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name2") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name3") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name4") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name5") { Set(Row(0) { Set(StringCell(0, "data")) }) }
                               ) : _*
                             )
    }
    val newSheet = Sheet("name3") { Set( Row(0) { Set(StringCell(0, "data")) } ) }
    Hello.addSheetToWorkbook(workbook, newSheet).safeToFile("/tmp/test.xls").fold(ex ⇒ throw ex, identity).unsafePerformIO
    Hello.addSheetToWorkbook(workbook, newSheet) shouldEqual Workbook {
      Set(Sheet("name") { Set(Row(0) { Set(StringCell(0, "data")) }) },
          Sheet("name2") { Set(Row(0) { Set(StringCell(0, "data")) }) },
          Sheet("name3") { Set(Row(0) { Set(StringCell(0, "data")) }) }
      )
    }
  }
}
