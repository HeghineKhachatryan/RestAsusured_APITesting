package model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String title;
    private String author;
    private String content;

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.content = builder.content;
    }

    public static String generateUsersWithTitleAndAuthor() {
        String title = "json-server " + System.currentTimeMillis();
        String author = "typicode " + System.currentTimeMillis();
        return "{\"title\":\"" + title + "\"," +
                "\"author\":\"" + author + " \"}";
    }

    public static String generateUsersWithTitleAuthorAndContent() {
        String title = "json-server " + System.currentTimeMillis();
        String author = "typicode " + System.currentTimeMillis();
        String content = "content of " + author;
        return "{\"title\":\"" + title + "\",\n" +
                "\"author\":\"" + author + " \",\n" +
                "\"content\":\"" + content + " \"\n}";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public String toString() {
        return "{\"title\":\"" + title + "\",\n"
                + "\"author\":\"" + author + "\",\n"
                + "\"content\":\"" + content+"\"\n}";
    }

    public static class UserBuilder {
        int id;
        String title;
        String author;
        String content;

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public UserBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public UserBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public User buildUser() {
            return new User(this);
        }

        @Override
        public String toString() {
            return "{\"title\":\"" + title + "\","
                    + "\"author\":\"" + author + "\","
                    + "\"content\":\"" + content+"\"}";
        }
    }

}
