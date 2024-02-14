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
        return "username='" + username + "\n" +
                " location='" + location + "\n" +
                " questionCount=" + questionCount +"\n"+
                " answerCount=" + answerCount +"\n"+
                " tags=" + String.join(",", tags)+ "\n"+
                " profileLink='" + profileLink + "\n" +
                " avatar='" + avatar + "\n";
    }
}
