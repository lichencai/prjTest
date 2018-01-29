package proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * 设置了CallbackFilter，上文中MyProxyFilter实现类的返回值对应Callback[]数组中的位置索引。
 */
public class MyProxyFilter implements CallbackFilter {

	@Override
	public int accept(Method arg0) {
		if("query".equalsIgnoreCase(arg0.getName()))     
            return 1;
		else if("update".equalsIgnoreCase(arg0.getName()))
			return 2;
        return 0;
	}

}
