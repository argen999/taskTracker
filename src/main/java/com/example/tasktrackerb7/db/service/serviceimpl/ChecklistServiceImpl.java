package com.example.tasktrackerb7.db.service.serviceimpl;

import com.example.tasktrackerb7.db.entities.Checklist;
import com.example.tasktrackerb7.db.entities.Item;
import com.example.tasktrackerb7.db.repository.ChecklistRepository;
import com.example.tasktrackerb7.db.service.ChecklistService;
import com.example.tasktrackerb7.dto.response.ChecklistResponse;
import com.example.tasktrackerb7.dto.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;

    public ChecklistResponse convertToResponse(Checklist checklist) {
        List<Item> items = new ArrayList<>();
        if (checklist.getItems() != null) {
            items = checklist.getItems();
        }

        List<ItemResponse> itemResponses = new ArrayList<>();

        int countOfItems = 0;
        int countOfCompletedItems = 0;
        if (items != null) {
            for (Item item : items) {
                countOfItems++;
                if (item.isDone()) {
                    countOfCompletedItems++;
                }
                itemResponses.add(new ItemResponse(item.getId(), item.getText(), item.isDone()));
            }
            int count;
            if(countOfCompletedItems <= 0) {
                count = 0;
            } else {
                count = (countOfCompletedItems * 100) / countOfItems;
            }
            checklist.setPercent(count);
            checklistRepository.save(checklist);
        }

        return new ChecklistResponse(checklist.getId(), checklist.getTitle(), countOfCompletedItems, countOfItems, checklist.getPercent(), itemResponses);
    }
}
