package demo.service;

import java.util.UUID;

import demo.domain.Item;
import demo.domain.Outbox;
import demo.repository.ItemRepository;
import demo.repository.OutboxRepository;
import demo.rest.api.CreateItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    private final OutboxRepository outboxEventRepository;

    private final String outboxDestination;

    public ItemService(@Autowired ItemRepository itemRepository,
                       @Autowired OutboxRepository outboxEventRepository,
                       @Value("${demo.outboxDestination}") String outboxDestination) {
        this.itemRepository = itemRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.outboxDestination = outboxDestination;
    }

    @Transactional
    public UUID process(CreateItemRequest request) {
        Item item = Item.builder()
                .name(request.getName())
                .build();
        UUID itemId = itemRepository.save(item).getId();
        Outbox outboxEvent = Outbox.builder()
                .version("v1")
                .payload(request.getName())
                .destination(outboxDestination)
                .timestamp(System.currentTimeMillis())
                .build();
        UUID outboxId = outboxEventRepository.save(outboxEvent).getId();
        log.info("Item created with id " + itemId + " - and Outbox event created with Id: {}", outboxId);
        return itemId;
    }
}
