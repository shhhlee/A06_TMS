public class TodoRecord {
    private TodoVO task;

    public TodoRecord(TodoVO task) {
        this.task = task;
    }

    public void completeTask() {
        task.markAsCompleted();
    }

    public boolean isCompleted() {
        return task.isCompleted();
    }
}
