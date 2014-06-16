package ollitos.syncprops;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class Props extends Properties {

    public static String SYNCPROPS_PREFIX = "_SyncProps.";
    private static String SYNC_DATE = SYNCPROPS_PREFIX + "SyncDate";
    private static String MODIFIED_DATE = SYNCPROPS_PREFIX + "ModifiedDate";
    private static String DATE_PATTERN = "YYYY-MM-dd-HH-mm-ss-SSS";
    private DateFormat _format;

    public Props() {
        TimeZone zone = TimeZone.getTimeZone("GMT+0");
        _format = new SimpleDateFormat(DATE_PATTERN);
        _format.getCalendar().setTimeZone(zone);
    }

    @Override
    public Object setProperty(String key, String value) {
        if (key.startsWith(SYNCPROPS_PREFIX)) {
            throw new IllegalArgumentException("Use of prefix " + SYNCPROPS_PREFIX + " is restricted to sync framework");
        }
        updateModifiedDate(new Date());
        return super.setProperty(key, value);
    }

    void updateModifiedDate(Date now) {
        if (now != null) {
            String date = _format.format(now);
            super.setProperty(MODIFIED_DATE, date);
        }
        else {
            remove(MODIFIED_DATE);
        }
    }

    void updateSyncDate(Date now) {
        if (now != null) {
            String date = _format.format(now);
            super.setProperty(SYNC_DATE, date);
        }
        else {
            remove(SYNC_DATE);
        }
    }

    private Date getDate(String key) {
        String v = getProperty(key);
        try {
            return _format.parse(v);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public Date modifiedDate() {
        return getDate(MODIFIED_DATE);
    }

    public Date syncDate() {
        return getDate(SYNC_DATE);
    }

    public boolean isPropsKey(String key){
        return key.startsWith(SYNCPROPS_PREFIX);
    }

    public Set<String> userKeySet(){
        Set<String> ks = new HashSet<>();

        for( Object k : keySet() ){
            String key = k.toString();
            if( isPropsKey(key) ){
                break;
            }
            ks.add(key);
        }
        return ks;
    }

    public static final Comparator<Props> USER_COMPARATOR = new Comparator<Props>() {
        @Override
        public int compare(Props o1, Props o2) {
            TreeSet<String> keys = new TreeSet<>();
            keys.addAll(o1.userKeySet());
            keys.addAll(o2.userKeySet());

            for( String k : keys ){
                if( !o1.containsKey(k) ){
                    return -1;
                }
                if( !o2.containsKey(k) ){
                    return 1;
                }
                int c = o1.getProperty(k).compareTo( o2.getProperty(k) );
                if( c != 0 ){
                    return c;
                }
            }
            return 0;
        }
    };

}
