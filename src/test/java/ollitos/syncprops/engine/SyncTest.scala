package ollitos.syncprops.engine

import org.scalatest.FlatSpec
import ollitos.syncprops.engine.Sync.SyncMode

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
class SyncTest extends FlatSpec{

  import ollitos.syncprops.Examples._

  "A new client props" should "copy all from server in twoWays" in{
    val clientProps = emptyProps(null,null)
    val ancestorProps = serverProps( date, date )
    val servProps = serverProps( date, date )
    val sync = createSync

    val props = sync.sync(ancestorProps, servProps, clientProps, SyncMode.twoWays )

    assert( props == servProps )
  }

}
