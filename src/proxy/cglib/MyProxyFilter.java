package proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * ������CallbackFilter��������MyProxyFilterʵ����ķ���ֵ��ӦCallback[]�����е�λ��������
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
