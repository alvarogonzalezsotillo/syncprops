package ollitos.syncprops.engine;

import ollitos.syncprops.Props;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class Sync {

    public static enum SyncMode{
        twoWays,
        minePreferred,
        theirsPreferred,
    }

    public abstract SyncResult sync( Props ancestor, Props mine, Props theirs, SyncMode mode );
}
