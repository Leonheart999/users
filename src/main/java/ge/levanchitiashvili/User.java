package ge.levanchitiashvili;




import lombok.Data;

import java.util.List;
@Data
public class User {
    private Long userId;
    private String username;
    private String profileLink;
    private String location;
    private Integer questionCount;
    private Integer answerCount;
    private String avatar;
    private List<String> tags;

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", profileLink='" + profileLink + '\'' +
                ", location='" + location + '\'' +
                ", questionCount=" + questionCount +
                ", answerCount=" + answerCount +
                ", avatar='" + avatar + '\'' +
                ", tags=" + String.join(",", tags);
    }
}
