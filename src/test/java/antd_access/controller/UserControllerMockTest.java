package antd_access.controller;

import antd_access.model.db.UserEntity;
import antd_access.model.req.user.UserReq;
import antd_access.model.req.user.UserVO;
import antd_access.model.resp.HandlerResp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerMockTest {

    @Autowired
    private MockMvc mockMvc ;

    @MockBean
    private UserController userController;

    @Test
    @DisplayName("未登录状态下获取当前用户Mock")
    void fetchCurrentUserNotLogin() throws Exception{

        when(userController.fetchCurrentUser(null)).thenReturn(
                HandlerResp.success("Success",new UserVO(){{
                    setUsername("admin");
                }})
        );


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/current")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                ;

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.total").doesNotExist())
                .andExpect(jsonPath("$.msg").value("Success"))
                .andExpect(jsonPath("$.data.username").value("admin"))
        ;
    }


}