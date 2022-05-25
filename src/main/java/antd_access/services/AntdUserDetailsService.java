package antd_access.services;

import antd_access.model.db.UserEntity;
import antd_access.repository.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AntdUserDetailsService implements UserDetailsService {

    final UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username) ;
//        if(user == null){
//            throw new UsernameNotFoundException("用户名不存在!");
//        }
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户名不存在!"));
    }

    @Transactional
    public UserEntity onLoginSuccess(long uid, HttpServletRequest request) {
        UserEntity userEntity = userRepository.findById(uid).orElse(null);
        assert userEntity != null;

        String uuid = UUID.randomUUID().toString();
        userEntity.setToken(uuid);
        userEntity.setLastLoginAt(System.currentTimeMillis()) ;
        return userRepository.save(userEntity);
    }


    public UserEntity fetchUserByTokenAndLoginTime(String token, long lastLoginAt){
        return userRepository.findByTokenAndLastLoginAt(token, lastLoginAt);
    }

}
