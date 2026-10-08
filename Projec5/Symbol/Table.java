package Symbol;


public class Table {
    private static class Binder {
        Object value;
        Symbol prevtop;
        Binder tail;
        int level;
        Binder(Object v, Symbol p, Binder t, int l) { value = v; prevtop = p; tail = t; level = l; }
    }
 
    private java.util.Dictionary<Symbol, Binder> dict = new java.util.Hashtable<Symbol, Binder>();
    private Symbol top;
    private Binder marks;
    private int level = 0;
 
    public Table() { }
 
    public Object get(Symbol key) {
        Binder e = dict.get(key);
        return (e == null) ? null : e.value;
    }
 
    public void put(Symbol key, Object value) {
        dict.put(key, new Binder(value, top, dict.get(key), level));
        top = key;
    }
 
    public void beginScope() {
        marks = new Binder(null, top, marks, level);
        top = null;
        level++;
    }
 
    public void endScope() {
        if (marks == null) return;
        while (top != null) {
            Binder e = dict.get(top);
            if (e.tail != null) dict.put(top, e.tail);
            else dict.remove(top);
            top = e.prevtop;
        }
        top = marks.prevtop;
        marks = marks.tail;
        level--;
    }
 
    public java.util.Enumeration<Symbol> keys() { return dict.keys(); }
}
