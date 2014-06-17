package ollitos.syncprops.engine

import java.util.logging.LogManager

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

import ollitos.syncprops.Examples._
import ollitos.syncprops.engine.SyncResult.ResultCode._
import ollitos.syncprops.engine.Sync.SyncMode._

import ollitos.syncprops.PropsOps._

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */


@RunWith(classOf[JUnitRunner])
class SyncTest extends FlatSpec {

  def configureJavaLogging = {
    val inputStream = this.getClass.getResourceAsStream("test-logging.properties");
    LogManager.getLogManager().readConfiguration(inputStream);
    println( "Loggers configured")
  }

  configureJavaLogging

  "A new client props" should "copy all from server in twoWays" in {
    val cliProps = emptyProps(null, null)
    val ancProps = serverProps(date, date)
    val servProps = serverProps(date, date)

    val result = Sync.sync(ancProps, servProps, cliProps, twoWays)

    ancProps.prettyPrint(System.out, "#ANCESTOR")
    servProps.prettyPrint(System.out, "#SERVER")
    cliProps.prettyPrint(System.out, "#CLIENT")
    result.merged.prettyPrint(System.out, "#MERGED")

    assert(result.code == ok)
    assert(result.merged userEquals servProps)
  }
  
  "A client with new props" should "synchronize the new props in towWays" in{
    val cliProps = clientProps( date, dateAfter )
    val ancProps = ancestorProps( dateBefore, dateBefore )
    val servProps = serverProps( date, dateAfter )

    val result = Sync.sync(ancProps, servProps, cliProps, twoWays)

    ancProps.prettyPrint(System.out, "#ANCESTOR")
    servProps.prettyPrint(System.out, "#SERVER")
    cliProps.prettyPrint(System.out, "#CLIENT")
    result.merged.prettyPrint(System.out, "#MERGED")

    assert(result.code == conflict)
    assert(result.merged()(matchK) == cliProps(matchK) )
    assert(result.merged()(onlyInClientK) == cliProps(onlyInClientK) )
    assert(result.merged()(onlyInServerK) == servProps(onlyInServerK) )
    assert(result.merged()(mismatchK) == servProps(mismatchK))
  }

}
