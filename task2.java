
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Book Class to represent each book in the library
class Book {
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true; // By default, new books are available
    }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public String getGenre() { return genre; }

    public boolean isAvailable() { return isAvailable; }

    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println(title + " has been borrowed.");
        } else {
            System.out.println(title + " is currently unavailable.");
        }
    }

    public void returnBook() {
        isAvailable = true;
        System.out.println(title + " has been returned.");
    }
}

// Patron Class to represent a library member
class Patron {
    private String name;
    private String contact;
    private List<Book> borrowedBooks;

    public Patron(String name, String contact) {
        this.name = name;
        this.contact = contact;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }

    public String getContact() { return contact; }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.borrowBook();
            borrowedBooks.add(book);
        } else {
            System.out.println("Cannot borrow " + book.getTitle() + ". It's unavailable.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("This book was not borrowed by " + name);
        }
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}

// Fine Calculation Class
class Fine {
    private static final int DAILY_FINE = 1; // 1 unit fine per day

    public static int calculateFine(int overdueDays) {
        return DAILY_FINE * overdueDays;
    }
}

// Library Class to manage books, patrons, and borrowing system
class Library {
    private List<Book> books;
    private List<Patron> patrons;

    public Library() {
        this.books = new ArrayList<>();
        this.patrons = new ArrayList<>();
    }

    // Add a book to the library
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Add a patron to the library
    public void addPatron(Patron patron) {
        patrons.add(patron);
        System.out.println("Patron added: " + patron.getName());
    }

    // Find a book by its title
    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Find a patron by name
    public Patron findPatronByName(String name) {
        for (Patron patron : patrons) {
            if (patron.getName().equalsIgnoreCase(name)) {
                return patron;
            }
        }
        return null;
    }

    // Display all books
    public void displayBooks() {
        System.out.println("\nLibrary Books:");
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() +
                ", Genre: " + book.getGenre() + ", Available: " + book.isAvailable());
        }
    }

    // Display all patrons
    public void displayPatrons() {
        System.out.println("\nPatron List:");
        for (Patron patron : patrons) {
            System.out.println("Name: " + patron.getName() + ", Contact: " + patron.getContact());
        }
    }

    // Generate a report of borrowed books
    public void generateBorrowingReport() {
        System.out.println("\nBorrowing Report:");
        for (Patron patron : patrons) {
            System.out.println("Patron: " + patron.getName());
            for (Book book : patron.getBorrowedBooks()) {
                System.out.println("  Borrowed Book: " + book.getTitle());
            }
        }
    }

    // Generate fine report for overdue books
    public void generateFineReport() {
        System.out.println("\nFine Report:");
        for (Patron patron : patrons) {
            for (Book book : patron.getBorrowedBooks()) {
                int overdueDays = (int) (Math.random() * 15); // Simulated overdue days
                int fine = Fine.calculateFine(overdueDays);
                System.out.println("Patron: " + patron.getName() + ", Book: " + book.getTitle() +
                    ", Overdue Days: " + overdueDays + ", Fine: " + fine);
            }
        }
    }
}

// Main Class with User Menu
public class library_management {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Library Management System Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Patron");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Patrons");
            System.out.println("7. Generate Borrowing Report");
            System.out.println("8. Generate Fine Report");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book genre: ");
                    String genre = scanner.nextLine();
                    library.addBook(new Book(title, author, genre));
                    break;

                case 2:
                    System.out.print("Enter patron name: ");
                    String patronName = scanner.nextLine();
                    System.out.print("Enter patron contact: ");
                    String contact = scanner.nextLine();
                    library.addPatron(new Patron(patronName, contact));
                    break;

                case 3:
                    System.out.print("Enter patron name: ");
                    patronName = scanner.nextLine();
                    Patron patron = library.findPatronByName(patronName);
                    if (patron != null) {
                        System.out.print("Enter book title: ");
                        title = scanner.nextLine();
                        Book book = library.findBookByTitle(title);
                        if (book != null) {
                            patron.borrowBook(book);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("Patron not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter patron name: ");
                    patronName = scanner.nextLine();
                    patron = library.findPatronByName(patronName);
                    if (patron != null) {
                        System.out.print("Enter book title: ");
                        title = scanner.nextLine();
                        Book book = library.findBookByTitle(title);
                        if (book != null) {
                            patron.returnBook(book);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("Patron not found.");
                    }
                    break;

                case 5:
                    library.displayBooks();
                    break;

                case 6:
                    library.displayPatrons();
                    break;

                case 7:
                    library.generateBorrowingReport();
                    break;

                case 8:
                    library.generateFineReport();
                    break;

                case 9:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }

        scanner.close();
    }
}
