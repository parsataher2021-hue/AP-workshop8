import java.io.Serializable;
import java.time.LocalDate;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String content;
    private LocalDate creationDate;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.creationDate = LocalDate.now();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return title + " (" + creationDate + ")";
    }
}