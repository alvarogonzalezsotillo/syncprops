package ollitos.syncprops.engine

import ollitos.syncprops.engine.SyncResult.ResultCode._
import org.scalatest.FlatSpec
import ollitos.syncprops.engine.Sync.SyncMode._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import ollitos.syncprops.PropsOps._

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */


@RunWith(classOf[JUnitRunner])
class SyncTest extends FlatSpec{

  import ollitos.syncprops.Examples._

  "A new client props" should "copy all from server in twoWays" in{
    val clientProps = emptyProps(null,null)
    val ancestorProps = serverProps( date, date )
    val servProps = serverProps( date, date )

    val result = Sync.sync(ancestorProps, servProps, clientProps, twoWays )

    assert( result.code == ok )
    assert( result.merged userEquals servProps )
  }

}
