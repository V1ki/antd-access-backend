package antd_access.controller;

import antd_access.model.req.user.UserReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc ;

    @Test
    @DisplayName("未登录状态下获取当前用户")
    void fetchCurrentUserNotLogin() throws Exception{


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/current")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                ;

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.total").doesNotExist())
                .andExpect(jsonPath("$.msg").value("用户未登录"))
        ;
    }

    @Test
    @DisplayName("登录状态下获取当前用户")
    @WithUserDetails(value="admin", userDetailsServiceBeanName="antdUserDetailsService")
    void fetchCurrentUserLoged() throws Exception {


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/current")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                ;

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.total").doesNotExist())
                .andExpect(jsonPath("$.msg").value("获取当前用户成功"))
                .andExpect(jsonPath("$.data.username").value("admin"))
        ;
    }


    @Test
    @WithAnonymousUser
    @DisplayName("未登录状态下修改用户")
    void updateUserWithAnonymousUser() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        UserReq userReq = new UserReq() ;
        userReq.setPassword("Abcd1234");
        userReq.setUsername("admin");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/admin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userReq))
                ;

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(401))
        ;
    }

    @Test
    @DisplayName("登录状态下修改用户")
    @WithUserDetails(value="admin", userDetailsServiceBeanName="antdUserDetailsService")
    void updateUserWithUserDetail() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        UserReq userReq = new UserReq() ;
        userReq.setPassword("Abcd1234");
        userReq.setUsername("admin");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/admin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userReq))
                ;

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
        ;
    }


}