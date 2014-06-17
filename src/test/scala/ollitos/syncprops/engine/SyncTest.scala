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
    val clientProps = emptyProps(null, null)
    val ancestorProps = serverProps(date, date)
    val servProps = serverProps(date, date)

    val result = Sync.sync(ancestorProps, servProps, clientProps, twoWays)

    ancestorProps.prettyPrint(System.out, "#ANCESTOR")
    servProps.prettyPrint(System.out, "#SERVER")
    result.merged.prettyPrint(System.out, "#MERGED")

    assert(result.code == ok)
    assert(result.merged userEquals servProps)
  }

}
