public class ParentClass {
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "RIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";

    public static final int INT_CONST = 100;

    private String stringField;
    private int intField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String value) {
        stringField = value;
    }

    public int getIntField() {
        return intField;
    }

    public void setIntField(int value) {
        intField = value;
    }

    private void privateParentMethod() {}
}
