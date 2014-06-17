package ollitos.syncprops.engine;

import ollitos.syncprops.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static ollitos.syncprops.engine.SyncResult.ResultCode.conflict;
import static ollitos.syncprops.engine.SyncResult.ResultCode.ok;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class Sync {

    final static Logger logger = LoggerFactory.getLogger(Sync.class);

    private Props _ancestor;
    private Props _master;
    private Props _slave;
    private boolean _merge;

    private Sync(Props ancestor, Props master, Props slave, boolean merge) {
        _ancestor = ancestor;
        _master = master;
        _slave = slave;
        _merge = merge;
    }

    private static boolean before(Date before, Date after) {
        if (before == null) {
            return true;
        }
        if (after == null) {
            return false;
        }
        return before.getTime() < after.getTime();
    }

    private static boolean between(Date before, Date between, Date after) {
        return before(before, after) &&
                before(before, between) &&
                before(between, after);
    }

    public static SyncResult sync(Props ancestor, Props mine, Props theirs, SyncMode mode) {
        Sync s;
        switch (mode) {
            case minePreferred:
                s = new Sync(ancestor, mine, theirs, false);
                break;
            case theirsPreferred:
                s = new Sync(ancestor, theirs, mine, false);
                break;
            case twoWays:
                s = new Sync(ancestor, mine, theirs, true);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return s.doSync();
    }

    private SyncResult doSync() {

        Set<String> allKS = allKeys();

        Set<String> conflicts = new HashSet<>();
        Props merged = new Props();

        for (String key : allKS) {
            SingleKeySyncResult ssr = syncKey(key, _ancestor.getProperty(key), _master.getProperty(key), _slave.getProperty(key));
            if( ssr.value != null ){  
                merged.setProperty(key, ssr.value);
            }
            if (ssr.resultCode != ok) {
                conflicts.add(ssr.key);
            }
        }

        return new SyncResult(merged, _master, _slave, conflicts);
    }
    
    private Date masterModified(){
      return _master.modifiedDate();
    }
    
    private Date slaveModified(){
      return _slave.modifiedDate();
    }

    private Set<String> allKeys() {
        Set<String> allKS = new HashSet<>();
        allKS.addAll(_ancestor.userKeySet());
        allKS.addAll(_master.userKeySet());
        allKS.addAll(_slave.userKeySet());
        return allKS;
    }

    private SingleKeySyncResult syncKey(String key, String ancestor, String master, String slave) {
        SingleKeySyncResult ret;

        if (_merge) {
            ret = syncKey_merge(key, ancestor, master, slave);
        }
        else {
            ret = syncKey_master(key, ancestor, master, slave);
        }

        debug( "syncKey" );
        debug( "  _merge  : " + _merge);
        debug( "  ancestor: " + ancestor );
        debug( "  master  : " + master );
        debug( "  slave   : " + slave );
        debug( "  ret     : " + ret.value + "  --  " + ret.resultCode );

        return ret;
    }
    
    private void debug( String s ){
      //logger.debug( s );
      System.out.println( s );
    }

    private SingleKeySyncResult syncKey_master(String key, String ancestor, String master, String slave) {
        if (master != null) {
            return new SingleKeySyncResult(key, master, ok);
        }
        else {
            return new SingleKeySyncResult(key, slave, ok);
        }
    }
    
    private  <T> boolean safeEQ( T o1, T o2 ){
        if( o1 == null && o2 == null ){
            return true;
        }
        if( o1 != null && o2 == null ){
            return false;
        }
        if( o1 == null && o2 != null ){
            return false;
        }
        return o1.equals(o2);
    }

    private SingleKeySyncResult syncKey_merge(String key, String ancestor, String master, String slave) {


        if ( safeEQ(master,slave) ) {
            // KEY UNCHANGED
            return new SingleKeySyncResult(key, master, ok);
        }

        if (ancestor == null) {
            if( slave == null ){
                // KEY IS NEW SINCE ANCESTOR AND IS ONLY IN MASTER
                return new SingleKeySyncResult(key,master,ok);
            }
        }
        
        if( safeEQ(master,ancestor) ){
            if( slave == null ){
                // KEY IS UNCHANGED IN MASTER, DOESNT EXIST IN SLAVE
                return new SingleKeySyncResult(key,master,ok);
            }
        }
        
        if( master == null && ancestor == null ){
            if( slave != null ){
                // KEY IS NEW IN SLAVE
                return new SingleKeySyncResult(key,slave,ok);
            }
        }

        // TODO
        return new SingleKeySyncResult(key,master,conflict);
    }

    public static enum SyncMode {
        twoWays,
        minePreferred,
        theirsPreferred,
    }

    private class SingleKeySyncResult {
        String key;
        String value;
        SyncResult.ResultCode resultCode;

        public SingleKeySyncResult(String key, String value, SyncResult.ResultCode resultCode) {
            this.key = key;
            this.value = value;
            this.resultCode = resultCode;
        }
    }
}
