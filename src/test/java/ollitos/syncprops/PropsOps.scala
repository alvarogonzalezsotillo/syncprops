package ollitos.syncprops

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 29/05/14
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */


class PropsOps( p : Props ) extends ((String)=>String){
  def apply( k : String ) = p.getProperty(k)
  def update( k: String, v: String ) = p.setProperty(k,v)
}

object PropsOps{
  implicit def toPropsOps( p : Props ) = new PropsOps(p)
}
