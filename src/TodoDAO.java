import java.io.*;
import java.util.ArrayList;

public class TodoDAO {
    private static final String FILE_PATH = "dataFiles/tasksList.txt";

    // 파일이 없으면 생성하는 메서드
    private void createFileIfNotExists() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("20240101 0000");
                writer.newLine();
            }
        }
    }

    public ArrayList<TodoVO> readTasks() throws IOException {
        createFileIfNotExists();
        ArrayList<TodoVO> tasks = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        reader.readLine(); // 첫 줄(최근 접속 일자)은 건너뜁니다
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("/");
            String title = parts[0];
            boolean isCompleted = parts.length > 1 && "완료".equals(parts[1]);
            String createdDate = parts.length > 2 ? parts[2] : "";
            String dueDate = parts.length > 3 ? parts[3] : "-";
            boolean canCompleteAfterDue = parts.length > 4 && "가능".equals(parts[4]);
            String id = parts.length > 5 ? parts[5] : "defaultId";
            tasks.add(new TodoVO(title, isCompleted, createdDate, dueDate, canCompleteAfterDue,id));
        }
        reader.close();
        return tasks;
    }

    // 할 일 목록을 파일에 기록하는 메서드 (첫 번째 줄은 최근 접속 일자 유지)
    public void writeTasks(String todayDateTime, ArrayList<TodoVO> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // 첫 줄에 오늘 날짜와 시간을 기록하여 최근 접속 일자 갱신
            writer.write(todayDateTime);
            writer.newLine();

            // 할 일 목록을 순차적으로 기록
            for (TodoVO task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
        }
    }

    // 최근 접속 일자를 파일에서 가져오는 메서드
    public String getLastDate() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return reader.readLine();
        } catch (IOException e) {
            return "20240101 0000";
        }
    }
}
