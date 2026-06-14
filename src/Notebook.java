import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Notebook {
    private static final String STORAGE_FILE = "notebook.ser";
    private List<Note> notes;

    public Notebook() {
        notes = loadNotes();
    }

    private List<Note> loadNotes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            return (List<Note>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ Error loading notes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveNotes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(notes);
        } catch (IOException e) {
            System.out.println("⚠️ Error saving notes: " + e.getMessage());
        }
    }

    public boolean addNote(Note note) {
        for (Note n : notes) {
            if (n.getTitle().equalsIgnoreCase(note.getTitle())) {
                return false;
            }
        }
        notes.add(note);
        saveNotes();
        return true;
    }

    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    public boolean removeNote(int index) {
        if (index >= 0 && index < notes.size()) {
            notes.remove(index);
            saveNotes();
            return true;
        }
        return false;
    }

    public boolean exportNote(int index, String exportDir) {
        if (index < 0 || index >= notes.size()) return false;

        Note note = notes.get(index);
        File dir = new File(exportDir);
        if (!dir.exists()) dir.mkdir();

        File file = new File(dir, note.getTitle() + ".txt");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Title: " + note.getTitle() + "\n");
            fw.write("Date: " + note.getCreationDate() + "\n");
            fw.write("Content:\n" + note.getContent());
            return true;
        } catch (IOException e) {
            System.out.println("⚠️ Error exporting note: " + e.getMessage());
            return false;
        }
    }
}