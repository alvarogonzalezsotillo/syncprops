package ollitos.syncprops;

import ollitos.syncprops.engine.Sync;
import ollitos.syncprops.engine.SyncResult;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public interface ServerPropsLocator extends PropsLocator{
    SyncResult sync(Props mine, Sync.SyncMode mode );
}
