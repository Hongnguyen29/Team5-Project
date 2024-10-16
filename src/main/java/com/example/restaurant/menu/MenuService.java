package com.example.restaurant.menu;

import com.example.restaurant.support.ImageFileUtils;
import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.menu.dto.MenuDto;
import com.example.restaurant.menu.dto.MenuViewDto;
import com.example.restaurant.menu.entity.MenuEntity;
import com.example.restaurant.menu.repo.MenuRepository;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final AuthenticationFacade facade;
    private final ImageFileUtils imageFileUtils;


    @Transactional
    public MenuViewDto addMenu(MenuDto dto){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = userRestaurant(user);
        RestaurantEntity restaurant1 =
                restaurantRepository.resMenus(restaurant.getId()).orElseThrow();
        checkShopStatus(restaurant.getId());
        String path = imageFileUtils.saveFile(
                String.format("restaurant/%d/",restaurant.getId()),
                dto.getNameFood()+"menu", dto.getFile()
                );
        MenuEntity menu = MenuEntity.builder()
                .restaurant(restaurant)
                .nameFood(dto.getNameFood())
                .price(dto.getPrice())
                .image(path)
                .restaurant(restaurant)
                .build();
        menuRepository.save(menu);
        log.info("--------------------------0-----------------");

        List<MenuEntity> menus = restaurant1.getMenus();
        log.info("----------------------------------1----------");
       // log.info(menus.toString());
        menus.add(menu);
        log.info("--------------------------------------------");
        //log.info(menus.toString());
        restaurantRepository.save(restaurant);
        MenuViewDto dto1 = MenuViewDto.fromEntity(menu);
        return dto1;
    }
    @Transactional
    public MenuViewDto updateMenu(
            Long menuId,
            MenuDto dto){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = userRestaurant(user);
        checkShopStatus(restaurant.getId());
        MenuEntity menu = checkMenu(menuId,restaurant);

        if(dto.getNameFood() != null && !(dto.getNameFood().isEmpty())){
            menu.setNameFood(dto.getNameFood());
        }
        if(dto.getPrice() !=null ){
            menu.setPrice(dto.getPrice());
        }
        if(dto.getFile()!=null && !(dto.getFile().isEmpty())){
            String path = imageFileUtils.saveFile(
                    String.format("restaurant/%d/",restaurant.getId()),
                    dto.getNameFood()+"menu", dto.getFile()
            );
            log.info("path:{}", path);
            menu.setImage(path);
        }
        if(dto.getStatus()!=null){
            menu.setStatus(dto.getStatus());
        }
        menuRepository.save(menu);

        return MenuViewDto.fromEntity(menu);
    }
    @Transactional
    public String deleteMenu(Long menuId){

        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = userRestaurant(user);
        checkShopStatus(restaurant.getId());
        MenuEntity menu = checkMenu(menuId,restaurant);

        menuRepository.deleteById(menuId);
        if(menuRepository.existsById(menuId)){
            throw new ResponseStatusException
                    (HttpStatus.SERVICE_UNAVAILABLE,"Error");
        }
        return "Deleted successfully.";
    }
    public MenuViewDto readOne(Long menuId){
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return MenuViewDto.fromEntity(menu);
    }
    public List<MenuViewDto> readAll(Long restId) {
        // Lấy tất cả menu của nhà hàng với ID đã cho
        List<MenuEntity> menuEntities = menuRepository.findAllByRestaurantId(restId);
        // Chuyển đổi từ List<MenuEntity> sang List<MenuViewDto> bằng cách sử dụng Stream
        return menuEntities.stream()
                .map(MenuViewDto::fromEntity) // Sử dụng map để chuyển đổi
                .collect(Collectors.toList()); // Thu thập lại thành List
    }
    public Page<MenuViewDto> readPage(Pageable pageable,Long restId) {  // tra ve cac trang menu
        RestaurantEntity restaurant = restaurantRepository.findById(restId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        return menuRepository.findAllByRestaurantId(restaurant.getId(), pageable)
                .map(MenuViewDto::fromEntity);
    }



   //helper method
    private void checkShopStatus(Long restId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!restaurant.getStatus().equals(RestStatus.OPEN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    private RestaurantEntity userRestaurant(UserEntity user){
        RestaurantEntity restaurant;
        try {
            restaurant = user.getRestaurant();
            if (restaurant == null) {
                throw new NullPointerException("Restaurant not found for user");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve restaurant", e);
        }
        return restaurant;
    }
    private MenuEntity checkMenu(Long menuId,RestaurantEntity restaurant){
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        RestaurantEntity restaurant1 = menu.getRestaurant();

        if (!restaurant1.getId().equals(restaurant.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "This menu is not in your restaurant");
        }
        return menu;
    }





}
