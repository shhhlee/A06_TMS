import java.util.ArrayList;

public class TodoVO {
    private ArrayList<String> attributes;  // 할 일 속성들을 담는 리스트
    private String id;
    private String content;

    public TodoVO(String title, boolean isCompleted, String createdDate, String dueDate, boolean canCompleteAfterDue, String id) {
        this.id = id;
        this.content = content;
        attributes = new ArrayList<>();
        attributes.add(id); // 첫 번째 속성: 할 일 ID
        attributes.add(title.trim());  // 두 번째 속성: 할 일 제목
        attributes.add(isCompleted ? "완료" : "진행중");  // 세 번째 속성: 완료 여부
        attributes.add(createdDate);  // 네 번째 속성: 생성일시
        attributes.add(dueDate != null ? dueDate : "-");  // 다섯 번째 속성: 마감 기한 (없을 경우 "-")
        attributes.add(canCompleteAfterDue ? "가능" : "불가능");  // 여섯 번째 속성: 마감 후 완료 가능 여부
    }

    // 할 일 ID 반환
    public String getId() {
        return attributes.get(0).trim();  // 첫 번째 속성은 ID
    }

    // 할 일 제목 반환
    public String getTitle() {
        return attributes.get(1).trim();  // 두 번째 속성은 할 일 제목
    }
    public void setContent(String newContent) {
        this.content = newContent;  // 내용 업데이트
    }
    // 할 일 제목 설정

    // 완료 여부 반환
    public boolean isCompleted() {
        return "완료".equals(attributes.get(2));  // 세 번째 속성은 완료 여부
    }

    // 완료로 표시하는 메서드
    public void markAsCompleted() {
        attributes.set(2, "완료");
    }

    // 생성일시 반환
    public String getCreatedDate() {
        return attributes.get(3).trim();
    }

    // 마감 기한 반환
    public String getDueDate() {
        return attributes.get(4).trim();
    }

    // 마감 후 완료 가능 여부 반환
    public boolean canCompleteAfterDue() {
        return "가능".equals(attributes.get(5));
    }

    @Override
    public String toString() {
        return getId() + " / " + getTitle() + " / " + (isCompleted() ? "완료" : "진행중") + " / " + getCreatedDate() + " / " + getDueDate() + " / " + (canCompleteAfterDue() ? "가능" : "불가능");
    }
}

