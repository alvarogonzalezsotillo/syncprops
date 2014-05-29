package ollitos.syncprops.engine;

import ollitos.syncprops.Props;

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
    private final ResultCode _code;

    public static enum ResultCode{
        ok,
        conflict
    }

    public SyncResult(Props merged, Props mine, Props theirs, ResultCode code ) {
        _merged = merged;
        _mine = mine;
        _theirs = theirs;
        _code = code;
    }

    public ResultCode code(){
        return _code;
    }

    public Props merged(){
        return _merged;
    }

}
