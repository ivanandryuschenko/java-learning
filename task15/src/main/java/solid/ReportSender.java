package solid;

public interface ReportSender {
    void send(Report report) throws SendReportException;
}
