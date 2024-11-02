package com.example.restaurant.restaurants;

import com.example.restaurant.support.ImageFileUtils;
import com.example.restaurant.auth.AuthenticationFacade;
import com.example.restaurant.auth.entity.UserEntity;
import com.example.restaurant.auth.repo.UserRepository;
import com.example.restaurant.enumList.Category;
import com.example.restaurant.enumList.RestStatus;
import com.example.restaurant.restaurants.dto.RestaurantDto;
import com.example.restaurant.restaurants.dto.RestaurantViewDto;
import com.example.restaurant.restaurants.entity.RestaurantEntity;
import com.example.restaurant.restaurants.repo.RestaurantRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final UserRepository userRepository;
    private final RestaurantRepository restRepository;
    private final AuthenticationFacade facade;
    private final ImageFileUtils imageFileUtils;

    public RestaurantViewDto myRestaurant(){
        RestaurantEntity restaurant = facade.extractUser().getRestaurant();
        return RestaurantViewDto.fromEntity(restaurant);
    }
    public RestaurantViewDto viewRestaurant(Long restaurantId){
        RestaurantEntity restaurant = restRepository.findById(restaurantId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return RestaurantViewDto.fromEntity(restaurant);
    }

    public List<RestaurantViewDto> searchRestaurants(String name, String address, Category category) {

        Specification<RestaurantEntity> spec = (root, query, criteriaBuilder) -> {
            // Kiểm tra nếu tất cả các điều kiện đều rỗng
            if ((name == null || name.isEmpty()) &&
                    (address == null || address.isEmpty()) &&
                    (category == null)) {
                // Nếu không có điều kiện nào, cho phép hiển thị tất cả các bản ghi
                return criteriaBuilder.conjunction(); // Hoặc criteriaBuilder.isTrue(), đều cho phép tất cả
            }

            Predicate predicate = criteriaBuilder.conjunction(); // Bắt đầu với điều kiện mặc định là TRUE (AND)

            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("nameRestaurant"), "%" + name + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }
            if (category != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category"), category));
            }

            try {
                UserEntity user = facade.extractUser();
                if (!(user.getUsername().equals("admin"))) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), RestStatus.OPEN));
                }
            } catch (Exception ignored) {}

            return predicate;
        };

        List<RestaurantEntity> entityList = restRepository.findAll(spec);
        List<RestaurantViewDto> dtoList = new ArrayList<>();
        for(RestaurantEntity o : entityList){
            dtoList.add(RestaurantViewDto.fromEntity(o));
        }
        return dtoList;
    }




    @Transactional
    public RestaurantViewDto updateInfo(RestaurantDto dto){
        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        if(restaurant.getStatus().equals(RestStatus.CLOSE)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if(dto.getAddress() != null && !(dto.getAddress().isEmpty())) {
            restaurant.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null && !(dto.getPhone().isEmpty())){
            restaurant.setPhone(dto.getPhone());
        }
        if(dto.getDescription() != null && !(dto.getDescription().isEmpty())){
            restaurant.setDescription(dto.getDescription());
        }
        if(dto.getCategory() != null){
            restaurant.setCategory(dto.getCategory());
        }
        if(dto.getOpenTime() != null){
            restaurant.setOpenTime(dto.getOpenTime());
        }
        if(dto.getCloseTime() != null){
            restaurant.setCloseTime(dto.getCloseTime());
        }
        if(dto.getAddress() != null
                && dto.getPhone() != null
                && dto.getDescription() != null
                && dto.getCategory() != null
                && dto.getOpenTime() != null
                && dto.getCloseTime() != null ){
            restaurant.setStatus(RestStatus.OPEN);
        }

        restRepository.save(restaurant);

        return RestaurantViewDto.fromEntity(restaurant);
    }
    @Transactional
    public RestaurantViewDto updateImage (MultipartFile file){

        UserEntity user = facade.extractUser();
        RestaurantEntity restaurant = user.getRestaurant();
        if(restaurant.getStatus().equals(RestStatus.CLOSE)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        String path = imageFileUtils.saveFile(
                String.format("users/%d/",user.getId()),
                user.getUsername()+"restImage", file
        );
        restaurant.setRestImage(path);
        restRepository.save(restaurant);
        return RestaurantViewDto.fromEntity(restaurant);
    }

    public RestaurantEntity findById(Long id){
        return restRepository.findById(id).orElseThrow();
    }



}
