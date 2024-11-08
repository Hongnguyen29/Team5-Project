package com.example.restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
    @GetMapping("/signup")
    public String signup(){
        return "signup";// vị trí file html (tính từ sau /templates, lược bỏ html -> ok)
    }
    @GetMapping("/login")
    public String signin(){
        return "login";// vị trí file html (tính từ sau /templates)
    }
    @GetMapping("/home")
    public String index(){
        return "home";
    }
    @GetMapping("/profile")
    public String profile(){
        return "/user/myprofile";
    }
    @GetMapping("/update")
    public String updateProfile(){
        return "/user/update";
    }
    @GetMapping("/openRest")
    public String openRestaurant(){
        return "/user/openRest";
    }
    @GetMapping("/reservation")
    public String reservationList(){
        return "/user/myReservation";
    }
    @GetMapping("/review")
    public String reviewList(){
        return "/user/myReview";
    }
    @GetMapping("/review/{id}")
    public String updateReview(){
        return "/user/updateReview";
    }
    @GetMapping("/reservation/{id}")
    public String createReview(){
        return "/user/createReview";
    }
    @GetMapping("/request")
    public String userRequest(){
        return "/user/myRequest";
    }


    @GetMapping("/restaurant/{id}")
    public String restView(){
        return "/restaurant/infoRest";  // thông tin của cửa hàng id  có bao gồm menu
    }
    @GetMapping("/restaurant/{id}/review")  // người dùng xem review của một nhà hàng id
    public String restReviews(){
        return "/restaurant/review";
    }
    @GetMapping("/restaurant/{id}/reservation") // người dùng đặt bàn tại nhà hàng id
    public String restReservation(){
        return "/restaurant/reservation";
    }
    @GetMapping("/myRestaurant")  // cập nhật thông tin của nhà hàng
    public String myRestaurant(){
        return "/myRestaurant/updateRest";
    }
    @GetMapping("/myRestaurant/review")  // xem review của nhà hàng  mình
    public String myRestReview(){
        return "/myRestaurant/reviewRest";
    }
    @GetMapping("/myRestaurant/reservation")  // xem review của nhà hàng  mình
    public String myRestReservation(){
        return "/myRestaurant/reservation-rest";
    }

    @GetMapping("/myRestaurant/menu")  // xem review của nhà hàng  mình
    public String myRestMenu(){
        return "/myRestaurant/myMenu";
    }
    @GetMapping("/myRestaurant/menu/create")  // xem review của nhà hàng  mình
    public String createMenu(){
        return "/myRestaurant/createMenu";
    }

    @GetMapping("/myRestaurant/menu/{menuId}")  // xem review của nhà hàng  mình
    public String updateMenu(){
        return "/myRestaurant/updateMenu";
    }
    @GetMapping("/myRestaurant/close")  // xem review của nhà hàng  mình
    public String closeRestaurant(){
        return "/myRestaurant/closeRequest";
    }


    @GetMapping("/admin/open-confirm")
    public String adminOpen(){
        return "/admin/open_confirm";
    }
    @GetMapping("/admin/close-confirm")
    public String adminClose(){
        return "/admin/close_confirm";
    }
    @GetMapping("/admin/users")
    public String allUsers(){
        return "/admin/allUsers";
    }




}
