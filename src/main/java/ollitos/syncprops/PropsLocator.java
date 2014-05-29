package ollitos.syncprops;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alvaro
 * Date: 26/05/14
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
public interface PropsLocator {
    Props readLatest() throws Exception;
    Props read( Date instant );
}
