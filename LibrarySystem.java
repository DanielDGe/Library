import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
// Sistema de Gestión de Biblioteca - Versión con problemas de diseño 
public class LibrarySystem {

    private List < Book > books = new ArrayList < > ();
    private List < Member > members = new ArrayList < > ();
    private List < Loan > loans = new ArrayList < > ();

    // Violación múltiple de SOLID 
    public void processLoan(String memberId, String bookId) {
        // Validar miembro 
        Member member = null;
        for (Member m: members) {
            if (m.getId().equals(memberId)) {
                member = m;
                break;
            }
        }
        if (member == null) {
            System.out.println("Miembro no encontrado");
            return;
        }

        // Validar libro 
        Book book = null;
        for (Book b: books) {
            if (b.getId().equals(bookId)) {
                book = b;
                break;
            }
        }
        if (book == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        // Verificar disponibilidad 
        if (!book.isAvailable()) {
            System.out.println("Libro no disponible");
            return;
        }

        // Verificar límite de préstamos 
        int activeLoans = 0;
        for (Loan loan: loans) {
            if (loan.getMemberId().equals(memberId) && !loan.isReturned())

            {
                activeLoans++;
            }
        }
        if (activeLoans >= 5) {
            System.out.println("Límite de préstamos alcanzado");
            return;
        }

        // Crear préstamo 
        Loan loan = new Loan(UUID.randomUUID().toString(), memberId,
            bookId, LocalDate.now());
        loans.add(loan);
        book.setAvailable(false);

        // Enviar notificación por email 
        System.out.println("Enviando email a " + member.getEmail() +
            ": Has prestado el libro " + book.getTitle());

        // Generar reporte 
        System.out.println("=== REPORTE DE PRÉSTAMO ===");
        System.out.println("Miembro: " + member.getName());
        System.out.println("Libro: " + book.getTitle());
        System.out.println("Fecha: " + LocalDate.now());
        System.out.println("Fecha de devolución: " +
            LocalDate.now().plusDays(14));
    }

    public void processReturn(String loanId) {
        Loan loan = null;
        for (Loan l: loans) {
            if (l.getId().equals(loanId)) {
                loan = l;
                break;
            }
        }
        if (loan == null) {
            System.out.println("Préstamo no encontrado");
            return;
        }

        loan.setReturned(true);


        Book book = null;
        for (Book b: books) {
            if (b.getId().equals(loan.getBookId())) {
                book = b;
                break;
            }
        }
        if (book != null) {
            book.setAvailable(true);
        }

        // Calcular multa si hay retraso 
        long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(),
            LocalDate.now());
        if (daysOverdue > 0) {
            double fine = daysOverdue * 0.50;
            System.out.println("Multa por retraso: $" + fine);
            // Aquí se guardaría en base de datos 
        }

        // Notificación 
        Member member = null;
        for (Member m: members) {
            if (m.getId().equals(loan.getMemberId())) {
                member = m;
                break;
            }
        }
        if (member != null) {
            System.out.println("Enviando email a " + member.getEmail() +
                ": Has devuelto el libro");
        }
    }

    public void generateMonthlyReport() {
        System.out.println("=== REPORTE MENSUAL ===");
        int totalLoans = 0;
        int totalReturns = 0;
        double totalFines = 0;

        for (Loan loan: loans) {

            if (loan.getLoanDate().getMonth() ==
                LocalDate.now().getMonth()) {
                totalLoans++;
                if (loan.isReturned()) {
                    totalReturns++;
                }
                if (loan.getDueDate().isBefore(LocalDate.now()) &&
                    !loan.isReturned()) {
                    long days = ChronoUnit.DAYS.between(loan.getDueDate(),
                        LocalDate.now());
                    totalFines += days * 0.50;
                }
            }
        }

        System.out.println("Préstamos: " + totalLoans);
        System.out.println("Devoluciones: " + totalReturns);
        System.out.println("Multas pendientes: $" + totalFines);
    }



    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();

        // Crear libros
        system.books.add(new Book("B1", "Clean Code", "Robert C. Martin"));
        system.books.add(new Book("B2", "Effective Java", "Joshua Bloch"));

        // Crear miembros
        system.members.add(new Member("M1", "Daniel", "daniel@email.com"));
        system.members.add(new Member("M2", "Ana", "ana@email.com"));

        // Procesar préstamo
        system.processLoan("M1", "B1");

        // Generar reporte mensual
        system.generateMonthlyReport();
    }

}

// Clases de soporte 
class Book {
    private String id;
    private String title;
    private String author;
    private boolean available;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available =
            available;
    }
}


class Member {
    private String id;
    private String name;
    private String email;

    public Member(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}

class Loan {
    private String id;
    private String memberId;
    private String bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean returned;

    public Loan(String id, String memberId, String bookId, LocalDate loanDate) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(14);
        this.returned = false;
    }

    public String getId() {
        return id;
    }
    public String getMemberId() {
        return memberId;
    }
    public String getBookId() {
        return bookId;
    }
    public LocalDate getLoanDate() {
        return loanDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public boolean isReturned() {
        return returned;
    }
    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}