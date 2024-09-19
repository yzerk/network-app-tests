package com.example.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AdminData {

    private String adminId;
    private String deviceId;
    private String email;
    private boolean emailAlertEnabled;
    private int emailAlertGroupingDelay;
    private boolean emailAlertGroupingEnabled;
    private boolean htmlEmailEnabled;

    @JsonProperty("is_owner")
    private boolean isOwner;

    @JsonProperty("is_professional_installer")
    private boolean isProfessionalInstaller;

    @JsonProperty("is_super")
    private boolean isSuper;
    private String lastSiteName;
    private String name;
    private boolean pushAlertEnabled;
    private boolean requiresNewPassword;
    private List<SuperSitePermissions> superSitePermissions;
    private UiSettings uiSettings;
    private String lastLoginIp;
    private int lastLoginTimestamp;

    @Data
    private static class UiSettings {
    }

    @Data
    private static class SuperSitePermissions {

    }
}

