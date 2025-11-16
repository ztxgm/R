public class TestClass {
    @Invoke(count = 2)
    protected void protectedMethod(String msg) {
        System.out.println("Protected: " + msg);
    }

    @Invoke(count = 3)
    private void privateMethod(int value) {
        System.out.println("Private: " + value);
    }

    public void publicMethod() {} // Не аннотирован
    protected void anotherProtected() {} // Не аннотирован
    private void anotherPrivate() {} // Не аннотирован
}