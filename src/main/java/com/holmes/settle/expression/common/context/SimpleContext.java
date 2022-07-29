package com.holmes.settle.expression.common.context;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 可以用来存储无序名值对
 */
public class SimpleContext extends AbstractContext {

    private final Map<String, Object> map;

    public SimpleContext() {
        this(new HashMap<>());
    }

    public SimpleContext(Map<String, Object> map) {
        this.map = map;
    }

    public int size() {
        return map.size();
    }

    public Context set(String name, Object value) {
        map.put(name, value);
        return this;
    }

    public Set<String> keys() {
        return map.keySet();
    }

    public boolean has(String key) {
        return map.containsKey(key);
    }

    public Map<String, Object> getInnerMap() {
        return map;
    }

    public Context clear() {
        this.map.clear();
        return this;
    }

    public Object get(String name) {
        return map.get(name);
    }

    public Object remove(String name) {
        return map.remove(name);
    }

    public SimpleContext clone() {
        SimpleContext context = new SimpleContext();
        context.map.putAll(this.map);
        return context;
    }

}
