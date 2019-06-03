package object;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class User
 */
public class User {
    public User(Integer id, String name, LocalDate birthday, Integer login_id, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.login_id = login_id;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    public Integer id;
    public String name;
    public LocalDate birthday;
    public Integer login_id;
    public String city;
    public String email;
    public String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(login_id, user.login_id) &&
                Objects.equals(city, user.city) &&
                Objects.equals(email, user.email) &&
                Objects.equals(description, user.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, login_id, city, email, description);
    }

    @Override
    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", login_id=" + login_id +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';

    }
}
