//package app.fastyleApplication.fastyle.model;
//
//import java.util.Collection;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.MappedSuperclass;
//
//
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//
//
//@MappedSuperclass
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @JoinTable(name="authorities_users", joinColumns= @JoinColumn(name="userId"), inverseJoinColumns = @JoinColumn(name="authorityId"))
//    @ManyToMany
//    private List<Authorities> authorities;
//    @Getter @Setter
//    private String username;
//    @Getter @Setter
//    private String firstName;
//    @Getter @Setter
//    private String lastName;
//    @Getter @Setter
//    private String email;
//    @Getter @Setter
//    private String password;
//    
//
////    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
////    @JoinTable(
////            name = "users_roles",
////            joinColumns = @JoinColumn(
////                    name = "user_id", referencedColumnName = "id"),
////            inverseJoinColumns = @JoinColumn(
////                    name = "role_id", referencedColumnName = "id"))
////    private Collection<Role> roles;
////
////    public User() {
////    }
////
////    public User(String firstName, String lastName, String email, String password) {
////        this.firstName = firstName;
////        this.lastName = lastName;
////        this.email = email;
////        this.password = password;
////    }
////
////    public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {
////        this.firstName = firstName;
////        this.lastName = lastName;
////        this.email = email;
////        this.password = password;
////        this.roles = roles;
////    }
//
//    
//    
//   
//  
//}
