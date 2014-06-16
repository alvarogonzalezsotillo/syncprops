package ollitos.syncprops.engine;

import ollitos.syncprops.Props;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class SyncResult {

    private final Props _theirs;
    private final Props _mine;
    private final Props _merged;
    private final Set<String> _conflicts;

    public static enum ResultCode{
        ok,
        conflict
    }

    public SyncResult(Props merged, Props mine, Props theirs, Set<String> conflicts ) {
        _merged = merged;
        _mine = mine;
        _theirs = theirs;
        _conflicts = conflicts;
    }

    public ResultCode code(){
        if( _conflicts == null || _conflicts.size() == 0 ){
            return ResultCode.ok;
        }
        else{
            return ResultCode.conflict;
        }
    }

    public Props merged(){
        return _merged;
    }

}
