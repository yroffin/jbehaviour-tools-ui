package service;

import java.util.Map;

import org.springframework.context.support.GenericApplicationContext;

import play.Play;

public class Spring {
	public static GenericApplicationContext applicationContext;

    public static Object getBean(String name) {
        if (applicationContext == null) {
            throw new RuntimeException("Unable to obtain spring bean because the application context has not been defined."); 
        }
        return applicationContext.getBean(name);
    }
    
	@SuppressWarnings("unchecked")
	public static <T> T getBeanOfType(Class<T> type) {
        Map<String, Object> beans = (Map<String, Object>) getBeansOfType(type);
        if(beans.isEmpty()) {
            return null;
        }
        return (T)beans.values().iterator().next();
    }
    
    public static Object getBeanOfType(String type) {
        try {
            return getBeanOfType(Play.application().classloader().loadClass(type));
        } catch (ClassNotFoundException ex) {
        	throw new RuntimeException(ex);
        }
    }
    
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        if (applicationContext == null) {
            throw new RuntimeException("Unable to obtain spring bean because the application context has not been defined."); 
        }
        return applicationContext.getBeansOfType(type);
    }
    
}