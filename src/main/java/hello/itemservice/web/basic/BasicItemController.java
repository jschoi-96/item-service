package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //    @Autowired
    //    public BasicItemController(ItemRepository itemRepository) {
    //        this.itemRepository = itemRepository;
    //    }
    // BasicItemController가 Bean으로 등록 -> itemRepository 생성자 주입
    // 생성자가 하나일 때 RequiredArgsConstructor로 대체 가능

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId , Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute(findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model
                       ) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        Item savedItem = itemRepository.save(item);

        model.addAttribute("item" , savedItem);
        return "basic/item";
    }

    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        Item savedItem = itemRepository.save(item);
        // model.addAttribute("item" , savedItem); // 자동 추가 되기때문에 생략 가능하다.
        return "redirect:/basic/items/" + item.getId();
    }

    /*
        return "basic/items/를 쓰지않는 이유
        웹 브라우저의 새로고침은 마지막에 서버에 전송한 데이터를 다시 전송한다
        만약에 리다이렉트를 하지 않는다면, 포스트가 계속 실행되고, 동일한 데이터가 계속 추가된다.
        리다이렉트를 하면, GET만 계속 전송하기 때문에 데이터 추가 X
     */

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }


    /*
        테스트용 데이터 추가
     */

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable long itemId){
        itemRepository.delete(itemId);
        return "redirect:/basic/items/";
    }



    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA" , 10000, 10));
        itemRepository.save(new Item("itemB" , 20000, 25));
    }
}
