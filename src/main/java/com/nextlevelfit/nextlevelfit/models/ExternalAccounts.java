package com.nextlevelfit.nextlevelfit.models;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Table(name = "external_accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "provider"})
})
@Entity

public class ExternalAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_external_account_user"))
    private User user;

    @Column(nullable = false)
    private String provider;   // 'strava', 'garmin', 'fitbit' etc.

    @Column(name = "external_user_id", nullable = false)
    private String externalUserId;  // User Id in provider

    @Column(name = "access_token", nullable = false, columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "refresh_tocken", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "token_expires_at")
    private String tokenExpiresAt;

    @Column()
    private String scope;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "updated_at")
    private Timestamp updatedA = new Timestamp(System.currentTimeMillis());
}

