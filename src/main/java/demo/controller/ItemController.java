package demo.controller;

import java.util.UUID;

import demo.rest.api.CreateItemRequest;
import demo.rest.api.CreateItemResponse;
import demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/item")
public class ItemController {

    @Autowired
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<CreateItemResponse> createItem(@RequestBody CreateItemRequest request) {
        log.info("Received request to create item with name: " + request.getName());
        try {
            UUID itemId = itemService.process(request);
            return ResponseEntity.ok(CreateItemResponse.builder().id(itemId).build());
        } catch(Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
