package solid.presentation;

import solid.receipt.LoanReceipt;
import solid.receipt.MonthlyReport;
import solid.receipt.ReturnReceipt;

public class ConsoleReportPresenter implements ReportPresenter {
    @Override
    public void presentMonthlyReport(MonthlyReport report) {
        System.out.println("=== REPORTE MENSUAL ===");
        System.out.println("Préstamos: " + report.getTotalLoans());
        System.out.println("Devoluciones: " + report.getTotalReturns());
        System.out.println("Multas pendientes: $" + report.getTotalFines());
    }

    @Override
    public void presentLoanReceipt(LoanReceipt receipt) {
        System.out.println("=== REPORTE DE PRÉSTAMO ===");
        System.out.println("Miembro: " + receipt.getMemberName());
        System.out.println("Libro: " + receipt.getBookTitle());
        System.out.println("Fecha: " + receipt.getLoanDate());
        System.out.println("Fecha de devolución: " + receipt.getDueDate());
    }

    @Override
    public void presentReturnReceipt(ReturnReceipt receipt) {
        if (receipt.getFine() > 0) {
            System.out.println("Multa por retraso: $" + receipt.getFine());
        }
    }
}
