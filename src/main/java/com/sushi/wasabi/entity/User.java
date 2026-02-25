package com.sushi.wasabi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sushi.wasabi.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "deviceTokens") // Prevents logging from crashing
@EqualsAndHashCode(exclude = "deviceTokens")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String qrCodeToken;

    @JsonIgnore
    @OneToMany
            (
                    mappedBy = "user",
                    orphanRemoval = true,
                    cascade = CascadeType.ALL
            )
    List<DeviceToken> deviceTokens = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public UserDto getUserInfo() {
        return UserDto.builder()
                .id(this.getId())
                .email(this.getEmail())
                .role(this.getRole())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .qrCodeToken(this.qrCodeToken)
                .build();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
