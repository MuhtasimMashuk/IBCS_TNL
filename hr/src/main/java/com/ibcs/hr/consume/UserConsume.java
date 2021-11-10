package com.ibcs.hr.consume;


import com.ibcs.hr.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ADMIN-SERVICE")
public interface UserConsume {

    @GetMapping("/admin/userApi/{id}")
    public UserDto getUserFromAdmin(@PathVariable Long id);


}
