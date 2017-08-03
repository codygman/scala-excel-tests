package example

import info.folone.scala.poi._
import scala.collection.immutable.ListSet
import java.io.{BufferedOutputStream,FileOutputStream}

object Hello extends App {

  def writeWorkbook() = {
    val workbook = Workbook { ListSet(
                               List(
                                 Sheet("name1") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name2") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name3") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name4") { Set(Row(0) { Set(StringCell(0, "data")) }) },
                                 Sheet("name5") { Set(Row(0) { Set(StringCell(0, "data")) }) }
                               ) : _*
                             )
    }.asPoi
    val sheetOrder = List("name1","name2","name3","name4","name5")
    for (sheetName <- sheetOrder; i <- 0 to sheetOrder.length-1){
      workbook.setSheetOrder(sheetName, i)
    }
    val file = "/tmp/test.xls"
    val target = new BufferedOutputStream( new FileOutputStream(file) );
    workbook.write(target)
    target.flush
    workbook.close

    println("done")

  }
  writeWorkbook()

  println("hi")
  // def addSheetToWorkbook(workbook : Workbook, newSheet : Sheet) : Workbook = {
  //   workbook
  // }
}


// notes below
// suggested answer
// Workbook(ListSet(sheets: _*))

// from issue
// Workbook(TreeSet(sheets : _*)(Ordering.fromLessThan[Sheet](_.name < _.name)))


/*
scala> import info.folone.scala.poi._
import info.folone.scala.poi._

scala> import scalaz._
import scalaz._

scala> import syntax.monoid._
import syntax.monoid._

scala> import syntax.foldable._
import syntax.foldable._

scala> import std.list._
import std.list._

scala> val sheetOne = Workbook {
   Set(Sheet("name") {
     Set(Row(1) {
       Set(NumericCell(1, 13.0/5), FormulaCell(2, "ABS(A1)"))
     },
     Row(2) {
       Set(StringCell(1, "data"), StringCell(2, "data2"))
     })
   },
   Sheet("name2") {
     Set(Row(2) {
       Set(BooleanCell(1, true), NumericCell(2, 2.4))
     })
   })
 }
sheetOne: info.folone.scala.poi.Workbook = Workbook(Set(Sheet ("name")(Set(Row (1)(Set(NumericCell(1, 2.6), FormulaCell(2, "=ABS(A1)"))), Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2"))))), Sheet ("name2")(Set(Row (2)(Set(BooleanCell(1, true), NumericCell(2, 2.4)))))))

scala> val path = "/tmp/workbook.xls"
path: String = /tmp/workbook.xls

scala> sheetOne.safeToFile(path).fold(ex ⇒ throw ex, identity).unsafePerformIO

scala> val sheetTwo = Workbook {
        Set(Sheet("name") {
          Set(Row(1) {
            Set(StringCell(1, "newdata"), StringCell(2, "data2"), StringCell(3, "data3"))
          },
          Row(2) {
            Set(StringCell(1, "data"), StringCell(2, "data2"))
          },
          Row(3) {
            Set(StringCell(1, "data"), StringCell(2, "data2"))
          })
        },
        Sheet("name") {
          Set(Row(2) {
            Set(StringCell(1, "data"), StringCell(2, "data2"))
          })
        })
      }
sheetTwo: info.folone.scala.poi.Workbook = Workbook(Set(Sheet ("name")(Set(Row (1)(Set(StringCell(1, "newdata"), StringCell(2, "data2"), StringCell(3, "data3"))), Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2"))), Row (3)(Set(StringCell(1, "data"), StringCell(2, "data2"))))), Sheet ("name")(Set(Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2")))))))

scala> import syntax.equal._
import syntax.equal._

scala> val res = Workbook(path).fold(
  ex       => false,
  workbook => (workbook |+| sheetTwo) === (sheetOne |+| sheetTwo)
)
res: scalaz.effect.IO[Boolean] = scalaz.effect.IOFunctions$$anon$5@7ad4ad93

scala> res.unsafePerformIO
res5: Boolean = true

scala> import impure._
import impure._

scala> sheetOne.overwrite(path)

scala> sheetOne
res7: info.folone.scala.poi.Workbook = Workbook(Set(Sheet ("name")(Set(Row (1)(Set(NumericCell(1, 2.6), FormulaCell(2, "=ABS(A1)"))), Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2"))))), Sheet ("name2")(Set(Row (2)(Set(BooleanCell(1, true), NumericCell(2, 2.4)))))))

scala> val mergeSheets = sheetOne |+| sheetTwo
mergeSheets: info.folone.scala.poi.Workbook = Workbook(Set(Sheet ("name2")(Set(Row (2)(Set(BooleanCell(1, true), NumericCell(2, 2.4))))), Sheet ("name")(Set(Row (1)(Set(NumericCell(1, 2.6), FormulaCell(2, "=ABS(A1)"))), Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2")))))))

scala> val sheetOneReloaded = load(path)
sheetOneReloaded: info.folone.scala.poi.Workbook = Workbook(Set(Sheet ("name2")(Set(Row (2)(Set(BooleanCell(1, true), NumericCell(2, 2.4))))), Sheet ("name")(Set(Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2"))), Row (1)(Set(NumericCell(1, 2.6), FormulaCell(2, "=ABS(A1)")))))))

scala> val mergeSheets2 = sheetOneReloaded |+| sheetTwo
mergeSheets2: info.folone.scala.poi.Workbook = Workbook(Set(Sheet ("name2")(Set(Row (2)(Set(BooleanCell(1, true), NumericCell(2, 2.4))))), Sheet ("name")(Set(Row (1)(Set(NumericCell(1, 2.6), FormulaCell(2, "=ABS(A1)"))), Row (2)(Set(StringCell(1, "data"), StringCell(2, "data2")))))))

scala> mergeSheets == mergeSheets2
res8: Boolean = true
 */
