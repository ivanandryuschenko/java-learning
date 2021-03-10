package solid;

import java.sql.Connection;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier implements ReportNotifier {
    private ReportGenerator reportGenerator;
    private ReportSender reportSender;

    public SalaryHtmlReportNotifier(Connection databaseConnection, String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        reportGenerator = new SalaryHtmlReportGenerator(databaseConnection, departmentId, dateFrom, dateTo);
        reportSender = new MailReportSender(recipients);
    }

    @Override
    public void report() {
        try {
            reportSender.send(reportGenerator.getReport());
        } catch (GenerateReportException e) {
            e.printStackTrace();
        } catch (SendReportException e) {
            e.printStackTrace();
        }
    }
}
