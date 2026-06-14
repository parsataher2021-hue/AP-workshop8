import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Notebook notebook = new Notebook();
    private static final String EXPORT_DIR = "export";

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addNote();
                    break;
                case 2:
                    removeNote();
                    break;
                case 3:
                    viewNotes();
                    break;
                case 4:
                    exportNote();
                    break;
                case 5:
                    System.out.println("👋 Exiting program...");
                    return;
                default:
                    System.out.println("❌ Invalid option! Try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Notebook ---");
        System.out.println("1- Add");
        System.out.println("2- Remove");
        System.out.println("3- Notes");
        System.out.println("4- Export");
        System.out.println("5- Exit");
        System.out.print("Your choice: ");
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private static void addNote() {
        System.out.print("Note title: ");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            System.out.println("❌ Title cannot be empty.");
            return;
        }

        System.out.println("Note content (press Enter on an empty line to finish):");
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            contentBuilder.append(line).append("\n");
        }

        Note note = new Note(title, contentBuilder.toString().trim());
        if (notebook.addNote(note)) {
            System.out.println("✅ Note added successfully.");
        } else {
            System.out.println("❌ Duplicate title! Note not added.");
        }
    }

    private static void removeNote() {
        List<Note> notes = notebook.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("⚠️ No notes available.");
            return;
        }

        System.out.println("List of notes (enter 0 to go back):");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + "- " + notes.get(i));
        }
        System.out.print("Note number to remove: ");
        int index = getIntInput();
        if (index == 0) {
            System.out.println("🔙 Back to main menu.");
            return;
        }
        if (notebook.removeNote(index - 1)) {
            System.out.println("✅ Note removed successfully.");
        } else {
            System.out.println("❌ Invalid number.");
        }
    }

    private static void viewNotes() {
        List<Note> notes = notebook.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("⚠️ No notes available.");
            return;
        }

        System.out.println("List of notes:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + "- " + notes.get(i));
        }
        System.out.print("Note number to view (0 to go back): ");
        int index = getIntInput();
        if (index == 0) return;
        if (index > 0 && index <= notes.size()) {
            Note note = notes.get(index - 1);
            System.out.println("\n--- " + note.getTitle() + " ---");
            System.out.println("Date: " + note.getCreationDate());
            System.out.println("Content:\n" + note.getContent());
            System.out.println("--------------------");
        } else {
            System.out.println("❌ Invalid number.");
        }
    }

    private static void exportNote() {
        List<Note> notes = notebook.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("⚠️ No notes to export.");
            return;
        }

        System.out.println("List of notes:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + "- " + notes.get(i));
        }
        System.out.print("Note number to export (0 to go back): ");
        int index = getIntInput();
        if (index == 0) return;
        if (notebook.exportNote(index - 1, EXPORT_DIR)) {
            System.out.println("✅ Export saved successfully in '" + EXPORT_DIR + "' folder.");
        } else {
            System.out.println("❌ Error exporting note.");
        }
    }
}