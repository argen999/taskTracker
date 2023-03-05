package com.example.tasktrackerb7.converter.responseConverter;

import com.example.tasktrackerb7.db.entities.Label;
import com.example.tasktrackerb7.dto.response.LabelResponse;
import org.springframework.stereotype.Component;

@Component
public class LabelResponseConverter {
    public LabelResponse viewLabel(Label label) {
        if (label == null) {
            return null;
        }
        LabelResponse labelResponse = new LabelResponse();
        labelResponse.setId(label.getId());
        labelResponse.setDescription(label.getDescription());
        labelResponse.setColor(label.getColor());
        return labelResponse;
    }
}
