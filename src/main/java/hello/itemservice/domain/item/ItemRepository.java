package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // -> ComponentScan의 대상이 된다
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static
    // 동시에 여러 스레드가 접근할때는 hashMap쓰면 안된다. ConcurrentHashMap을 써야
    private static Long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item );
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        // 프로적트 규모가 커지면, ItemDto를 만들어서 따로 관리를 해줘야함
    }

    public void delete(Long itemId){
        store.remove(itemId);
    }

    public void clearStore() {
        store.clear();
    }
}
