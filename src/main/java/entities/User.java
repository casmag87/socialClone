package entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <-- primary key
    private Long id;

    @Column(name = "user_name", length = 20)
    private String name;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), // now points to primary key
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "role_name")
    )
    private List<Role> roleList = new ArrayList<>();

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) return null;
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach(role -> rolesAsStrings.add(role.getRoleName()));
        return rolesAsStrings;
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, userPass);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts;


    public User(String name, String userPass) {
        this.name = name;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public User() {} // <-- default constructor required by JPA

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserPass() { return userPass; }
    public void setUserPass(String userPass) {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Role> getRoleList() { return roleList; }
    public void setRoleList(List<Role> roleList) { this.roleList = roleList; }

}

