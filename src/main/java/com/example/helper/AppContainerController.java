package com.example.helper;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Log
public class AppContainerController {

    public static final String DOCKER_DOWN_CMD = "docker compose down";
    public static final String DOCKER_UP_CMD = "docker compose up -d";
    public static final String DOCKER_CHECK_STATUS_CMD = "docker inspect -f {{.State.Running}} unifi-network-application";

    public static final String APP_URL = PropertyLoader.getProperty("baseUrl");
    public static final String DOCKER_WEB_SERVER_STATUS_CMD = "docker exec unifi-network-application curl -sk -w %{http_code}\\n " + APP_URL;

    public void restartApp() {
        appContainerDown();
        appContainerUp();
    }
    private void appContainerDown() {
        if(isDockerContainerUp()) {
            execute(DOCKER_DOWN_CMD);
            await().atMost(60, TimeUnit.SECONDS)
                    .pollInterval(1, TimeUnit.SECONDS)
                    .until(() -> !isDockerContainerUp());
        }
    }

    public void appContainerUp() {
        if(!isDockerContainerUp()) {
            execute(DOCKER_UP_CMD);
            await().atMost(60, TimeUnit.SECONDS)
                    .pollInterval(1, TimeUnit.SECONDS)
                    .until(()-> isDockerContainerUp() && isWebServerUp());
        }
    }

    private boolean isDockerContainerUp() {
        String result = execute(DOCKER_CHECK_STATUS_CMD);
        return Boolean.parseBoolean(result.trim());
    }

    private boolean isWebServerUp() {
        return "302".equals(execute(DOCKER_WEB_SERVER_STATUS_CMD).trim());
    }

    public String execute(String command) {
        try {
            log.info("Execution command: " + command);
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                result.append(line);
            }
            process.waitFor();
            return result.toString();
        } catch (IOException | InterruptedException e) {
            log.warning(e.getMessage());
            throw new AssertionError("Can not execute command: " + command);
        }
    }
}
