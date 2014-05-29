package ollitos.syncprops;

import ollitos.syncprops.engine.Sync;
import ollitos.syncprops.engine.SyncResult;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class SyncedProps {
    private final ServerPropsLocator _remoteLocator;
    private final OwnPropsLocator _ownLocator;
    private final Sync.SyncMode _mode;

    public SyncedProps( OwnPropsLocator ownLocator, ServerPropsLocator serverLocator, Sync.SyncMode mode ){
        _ownLocator = ownLocator;
        _remoteLocator = serverLocator;
        _mode = mode;
    }

    public SyncResult sync() throws Exception {
        Props mine = _ownLocator.readLatest();
        SyncResult result = _remoteLocator.sync(mine, _mode);
        if( result.code() == SyncResult.ResultCode.ok ){
            _ownLocator.write(result.merged() );
        }
        return result;
    }
}
