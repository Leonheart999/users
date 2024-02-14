package ge.levanchitiashvili;


public class Main {
    public static void main(String[] args) {
        HTTPSRequestService.getFilteredUsers().forEach(user -> System.out.println(user.toString()));
    }


}