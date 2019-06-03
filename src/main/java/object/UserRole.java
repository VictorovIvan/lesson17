package object;

import java.util.Objects;

/**
 * Class UserRole
 */
public class UserRole {
    public UserRole(Integer id, Integer user_id, Integer role_id) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public Integer id;
    public Integer user_id;
    public Integer role_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) &&
                Objects.equals(user_id, userRole.user_id) &&
                Objects.equals(role_id, userRole.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, role_id);
    }

    @Override
    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", role_id=" + role_id +
                '}';
    }
}
