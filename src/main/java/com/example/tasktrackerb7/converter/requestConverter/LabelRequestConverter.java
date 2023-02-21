package com.example.tasktrackerb7.converter.requestConverter;

import com.example.tasktrackerb7.db.entities.Label;
import com.example.tasktrackerb7.dto.request.LabelRequest;
import org.springframework.stereotype.Component;

@Component
public class LabelRequestConverter {

    public Label createLabel(LabelRequest labelRequest) {
        if (labelRequest == null) {
            return null;
        }
        Label label = new Label();
        label.setDescription(labelRequest.getDescription());
        label.setColor(labelRequest.getColor());
        return label;
    }

    public void updateLabel(Label label, LabelRequest labelRequest) {
        if (labelRequest.getDescription() != null) {
            label.setDescription(labelRequest.getDescription());
        }
        if (labelRequest.getColor() != null) {
            label.setColor(labelRequest.getColor());
        }
    }
}
