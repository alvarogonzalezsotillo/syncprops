package ollitos.syncprops

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
object Examples {

  import PropsOps._


  val matchK = "match"
  val onlyInClientK = "onlyInClient"
  val onlyInServerK = "onlyInServer"
  val mismatchK = "mismatch"


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
