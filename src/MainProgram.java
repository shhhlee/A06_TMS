import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProgram {
    private Process1 process1;
    private Process2 process2;
    private Process3 process3;
    private final String lastDate;
    private String todayDateTime; // 프로그램 처음 시작 시 입력받는 현재 일시 (YYYYMMDD HHMM)
    private ArrayList<TodoVO> tasks;
    private TodoDAO dao;

    public MainProgram() {
        dao = new TodoDAO();
        lastDate = dao.getLastDate(); // 최근 접속 일자 가져오기
    }

    public void run() throws IOException {
        tasks = new ArrayList<>();
        // 최근 접속 일자 초기화 (임의의 값 설정)
        dao = new TodoDAO();
        tasks = dao.readTasks();
        inputDateTime();

        while (true) {
            printMainMenu();
            int menu = getUserInput(); // 메뉴 입력값을 정수로 받음

            if (menu == 4) { // 종료 조건
                System.out.println("> A06 TMS 프로그램을 종료합니다.");
                dao.writeTasks(todayDateTime, tasks); // 종료 시 현재 일시 저장
                break;
            }

            switch (menu) {
                case 1 -> process1 = new Process1(tasks, todayDateTime); // todayDateTime과 tasks 전달
                case 2 -> process2 = new Process2(tasks, todayDateTime);
                case 3 -> process3 = new Process3(tasks, todayDateTime);
            }

            System.out.println("------------------------------------------------------------");
        }
    }

    // 오늘 날짜와 시간을 입력받는 메서드
    private void inputDateTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("> A06 To-do list Management System (TMS)");
        System.out.println("이전 입력 일시: " + lastDate);
        while (true) {
            System.out.print("> “현재 일시(년월일+시분)”를 입력하세요 > ");
            String input = scanner.nextLine().trim();
            System.out.println("------------------------------------------------------------");

            if (!isValidDateTime(input)) {
                System.out.println("입력 가능한 문자열이 아닙니다.");
            } else if (!isAfterLastDateTime(input)) {
                System.out.println("이전 입력 일시 이후의 일시를 입력해주세요.");
            } else {
                this.todayDateTime = input;
                break;
            }
        }
    }

    // 날짜와 시간 유효성 검사 메서드
    private boolean isValidDateTime(String dateTime) {
        if (dateTime == null || dateTime.length() != 13 || dateTime.charAt(8) != ' ') {
            return false;
        }
        try {
            int year = Integer.parseInt(dateTime.substring(0, 4));
            int month = Integer.parseInt(dateTime.substring(4, 6));
            int day = Integer.parseInt(dateTime.substring(6, 8));
            int hour = Integer.parseInt(dateTime.substring(9, 11));
            int minute = Integer.parseInt(dateTime.substring(11, 13));
            return isDateInRange(year, month, day, hour, minute);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDateInRange(int year, int month, int day, int hour, int minute) {
        return year >= 1990 && year <= 2101 &&
                month >= 1 && month <= 12 &&
                day >= 1 && day <= (month == 2 ? (isLeapYear(year) ? 29 : 28) : (monthHas31Days(month) ? 31 : 30)) &&
                hour >= 0 && hour <= 23 &&
                minute >= 0 && minute <= 59;
    }

    private boolean isAfterLastDateTime(String dateTime) {
        int year = Integer.parseInt(dateTime.substring(0, 4));
        int month = Integer.parseInt(dateTime.substring(4, 6));
        int day = Integer.parseInt(dateTime.substring(6, 8));
        int hour = Integer.parseInt(dateTime.substring(9, 11));
        int minute = Integer.parseInt(dateTime.substring(11, 13));

        int lastYear = Integer.parseInt(lastDate.substring(0, 4));
        int lastMonth = Integer.parseInt(lastDate.substring(4, 6));
        int lastDay = Integer.parseInt(lastDate.substring(6, 8));
        int lastHour = Integer.parseInt(lastDate.substring(9, 11));
        int lastMinute = Integer.parseInt(lastDate.substring(11, 13));

        // 입력 날짜와 시간이 최근 접속 일자 이후인지 확인
        return year > lastYear ||
                (year == lastYear && (month > lastMonth ||
                        (month == lastMonth && (day > lastDay ||
                                (day == lastDay && (hour > lastHour || (hour == lastHour && minute >= lastMinute)))))));
    }
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private boolean monthHas31Days(int month) {
        return month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12;
    }

    private void printMainMenu() {
        System.out.println("> A06 TMS");
        System.out.println("1) Add To-do (할 일 추가)");
        System.out.println("2) Check To-do list (투두리스트 조회)");
        System.out.println("3) Manage To-do list (투두리스트 관리)");
        System.out.println("4) Quit (종료)");
        System.out.println("------------------------------------------------------------");
        System.out.print("> A06 TMS : menu > ");
    }

    private int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.length() != 1) {
            System.out.println("잘못 입력했습니다. 범위(1~4) 안에서 다시 선택해주세요.");
            return -1; // 유효하지 않은 입력
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= 4) {
                return choice; // 유효한 메뉴 선택 반환
            } else {
                System.out.println("잘못 입력했습니다. 범위(1~4) 안에서 다시 선택해주세요.");
                return -1; // 유효하지 않은 선택
            }
        } catch (NumberFormatException e) {
            System.out.println("잘못 입력했습니다. 범위(1~4) 안에서 다시 선택해주세요.");
            return -1; // 유효하지 않은 입력
        }
    }
}
