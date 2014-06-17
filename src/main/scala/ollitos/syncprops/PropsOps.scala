package ollitos.syncprops

import java.io.{PrintStream, PrintWriter}


/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */


class PropsOps( p : ollitos.syncprops.Props ) extends ((String)=>String){
  def apply( k : String ) = p.getProperty(k)
  def update( k: String, v: String ) = p.setProperty(k,v)
  def userEquals( props: Props ) = PropsOps.userEquals(p,props)
  def prettyPrint( out: PrintStream, header: String = "" ){
    import scala.collection.JavaConversions._
    val k = p.userKeySet.toList.sorted
    if( header != "" ){
      out.println( header )
    }
    k.foreach( key => out.println( s"$key = ${p(key)}" ))
  }
}

object PropsOps{
  import scala.language.implicitConversions
  implicit def toPropsOps( p : Props ) = new PropsOps(p)

  def userEquals( p1: Props, p2: Props ) = {

    import scala.collection.JavaConversions._

    val k1 = p1.userKeySet().toSet
    val k2 = p1.userKeySet().toSet

    if( k1 != k2 ){
      false
    }
    else{
      k1.forall( k => p1(k) == p2(k) )
    }
  }
}
