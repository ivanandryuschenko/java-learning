package solid;

public class HtmlReport implements Report {
    private String report;

    public HtmlReport(String report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return report;
    }
}
