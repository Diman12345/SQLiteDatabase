package training.ipb.sqlite.database;

public class DatabaseNote {
    public static final String TABLE_NAME = "notes";

    public static final String _ID = "id";
    public static final String NOTE = "note";
    public static final String TIME = "time";

    private int id;
    private String note;
    private String time;

    public static final String CREATE_TABLE =
            "CREATE_TABLE"+ TABLE_NAME + "("
                    +_ID + "INTEGER, PRIMARY KEY AUTOINCREMENT,"
                    + NOTE + "TEXT,"
                    + TIME + "DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    public DatabaseNote(){

    }

    public DatabaseNote(int id, String note, String time){
        this.id = id;
        this.note = note;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
