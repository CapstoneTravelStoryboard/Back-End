package com.example.travel_sculptor.util;

import com.example.travel_sculptor.domain.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneDescriptionGenerator {

    public List<String> generateSceneDescriptions(List<Scene> scenes) {
        List<String> sceneDescriptionList = new ArrayList<>();

        for (Scene scene : scenes) {
            StringBuilder descriptionBuilder = new StringBuilder();

            // Add the scene title
            descriptionBuilder.append("- scene ")
                    .append(scene.getOrderNum())
                    .append(" \"")
                    .append(scene.getTitle())
                    .append("\":\n");

            // Add description
            descriptionBuilder.append("    1. **영상**: ")
                    .append(scene.getDescription())
                    .append("\n");

            // Add camera angle
            descriptionBuilder.append("    2. **화각**: ")
                    .append(scene.getCameraAngle())
                    .append("\n");

            // Add camera movement
            descriptionBuilder.append("    3. **카메라 무빙**: ")
                    .append(scene.getCameraMovement())
                    .append("\n");

            // Add composition
            descriptionBuilder.append("    4. **구도**: ")
                    .append(scene.getComposition())
                    .append("\n");

            // Add to the result list
            sceneDescriptionList.add(descriptionBuilder.toString());
        }

        return sceneDescriptionList;
    }
}