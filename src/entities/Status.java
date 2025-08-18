package entities;

public enum Status {
    TODO,
    DOING,
    DONE;

    public static Status parseFromString(String status) {
        if (status.equals("TODO")) {
            return Status.TODO;
        } else if (status.equals("DOING")) {
            return Status.DOING;
        }  else if (status.equals("DONE")) {
            return Status.DONE;
        }
        return null;
    }
}
