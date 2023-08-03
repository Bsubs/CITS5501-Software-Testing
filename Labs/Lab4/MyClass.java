package Labs.Lab4;

public class MyClass {
    private int x;
    public MyClass(int x) { this.x = x; }
 
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof MyClass)) return false;
       return ((MyClass) obj).x == this.x;
    }
}
