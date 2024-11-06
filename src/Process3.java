import java.util.ArrayList;
import java.util.Scanner;

public class Process3 {
    private ArrayList<TodoVO> tasks;
    private String todayDateTime;
    private Scanner scanner;

    public Process3(ArrayList<TodoVO> tasks, String todayDateTime) {
        this.tasks = tasks;
        this.todayDateTime = todayDateTime;
        this.scanner = new Scanner(System.in);
        manageTasks();
    }

    // To-do 리스트 관리 메서드
    private void manageTasks() {
        while (true) {
            printManageMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1 -> editTask();  // To-do 항목 편집
                case 2 -> deleteTask(); // To-do 항목 삭제
                case 3 -> {
                    System.out.println("메인 메뉴로 돌아갑니다...");
                    return;
                }
                default -> System.out.println("유효하지 않은 선택입니다. 다시 시도하세요.");
            }
        }
    }

    private void printManageMenu() {
        System.out.println("3) Manage To-do list (투두리스트 관리)");
        System.out.println("1) Edit To-do (할 일 편집)");
        System.out.println("2) Delete To-do (할 일 삭제)");
        System.out.println("3) Return to Main Menu (메인 메뉴로 돌아가기)");
        System.out.println("------------------------------------------------------------");
        System.out.print("> 선택: ");
    }

    private int getUserInput() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 입력입니다. 숫자를 입력하세요.");
            return -1;
        }
    }

    private void editTask() {
        System.out.print("편집할 할 일 ID를 입력하세요: ");
        int taskId = getUserInput();

        TodoVO task = findTaskById(taskId);
        if (task != null) {
            System.out.print("새 할 일 내용을 입력하세요: ");
            String newContent = scanner.nextLine();
            task.setContent(newContent);
            System.out.println("할 일이 업데이트되었습니다.");
        } else {
            System.out.println("해당 ID의 할 일을 찾을 수 없습니다.");
        }
    }

    private void deleteTask() {
        System.out.print("삭제할 할 일 ID를 입력하세요: ");
        int taskId = getUserInput();

        TodoVO task = findTaskById(taskId);
        if (task != null) {
            tasks.remove(task);
            System.out.println("할 일이 삭제되었습니다.");
        } else {
            System.out.println("해당 ID의 할 일을 찾을 수 없습니다.");
        }
    }

    private TodoVO findTaskById(int taskId) {
        for (TodoVO task : tasks) {
            if (task.getId().equals(String.valueOf(taskId))) {
                return task;
            }
        }
        return null;
    }
}
