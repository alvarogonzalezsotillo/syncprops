package ollitos.syncprops

import ollitos.syncprops.engine.{SyncResult, Sync}
import ollitos.syncprops.engine.Sync.SyncMode


/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
object Examples {

  import PropsOps._
  import java.util.Date
  import java.text.SimpleDateFormat
  import scala.util.Try


  val matchK = "match"
  val onlyInClientK = "onlyInClient"
  val onlyInServerK = "onlyInServer"
  val mismatchK = "mismatch"

  def date : Date = new Date

  def date( s: String ) : Date = {
    val formats = Seq("YYYY-MM-dd-HH-mm-ss-SSS",
                      "YYYY-MM-dd-HH-mm-ss",
                      "YYYY-MM-dd-HH-mm",
                      "YYYY-MM-dd-HH",
                      "YYYY-MM-dd" )

    formats.map( new SimpleDateFormat(_) ).
            map( df => Try( df.parse(s) ) ).
            find( _.isSuccess ).get.get
  }

  def emptyProps(synced: Date, modified: Date) = {
    val ret = new Props()

    ret.updateModifiedDate(modified)
    ret.updateSyncDate(synced)

    ret
  }


  def serverProps(synced: Date, modified: Date) = {
    val ret = new Props()

    ret(matchK) = "same"
    ret(onlyInServerK) = onlyInServerK
    ret(mismatchK) = "serverValue"

    ret.updateModifiedDate(modified)
    ret.updateSyncDate(synced)

    ret
  }

  def clientProps(synced: Date, modified: Date) = {
    val ret = new Props()

    ret(matchK) = "same"
    ret(onlyInClientK) = onlyInClientK
    ret(mismatchK) = "clientValue"

    ret.updateModifiedDate(modified)
    ret.updateSyncDate(synced)

    ret
  }

  def ancestorProps(synced: Date,
                    modified: Date,
                    onlyClient: Boolean = false,
                    onlyServer: Boolean = false,
                    mismatchIsClient: Boolean = false,
                    mismatchIsServer: Boolean = false) = {
    val ret = new Props()

    ret(matchK) = "same"

    if (onlyClient) {
      ret(onlyInClientK) = onlyInClientK
    }

    if (onlyServer) {
      ret(onlyInServerK) = onlyInServerK
    }

    if (mismatchIsServer) {
      ret(mismatchK) = "serverValue"
    }

    if (mismatchIsClient) {
      ret(mismatchK) = "clientValue"
    }

    ret.updateModifiedDate(modified)
    ret.updateSyncDate(synced)

    ret
  }
}
